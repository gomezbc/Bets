package dataAccess;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.*;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess implements DataAccessInterface{
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		
	}

	public DataAccess()  {	
		 this(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}

			List<String> eventNames = new ArrayList(Arrays.asList("Atletico-Athletic","Eibar-Barcelona","Getafe-Celta","Alaves-Deportivo","Espanyol-Villareal",
					"Las Palmas-Sevilla","Malaga-Valencia","Girona-Leganés","Real Sociedad-Levante","Betis-Real Madrid", "Atletico-Athletic", "Eibar-Barcelona",
					"Getafe-Celta", "Alaves-Deportivo", "Espanyol-Villareal", "Las Palmas-Sevilla", "Málaga-Valencia", "Girona-Leganés", "Real Sociedad-Levante", "Betis-Real Madrid"));

			List<Event> events = createEvents(eventNames, year, month);

			List<String>[] questionNames = new ArrayList[]{
					new ArrayList(Arrays.asList("¿Quién ganará el partido?", "¿Quién meterá el primer gol?", "¿Cuántos goles se marcarán?", "¿Habrá goles en la primera parte?")),
					new ArrayList(Arrays.asList("Who will win the match?", "Who will score first?", "How many goals will be scored in the match?", "Will there be goals in the first half?")),
					new ArrayList(Arrays.asList("Zeinek irabaziko du partidua?", "Zeinek sartuko du lehenengo gola?", "Zenbat gol sartuko dira?", "Golak sartuko dira lehenengo zatian?"))
			};

			List<Question> questions = createQuestionsByLanguaje(questionNames, events);
					
			questions.get(0).addForecast("Athletic", 1.5f ,questions.get(0));
			questions.get(0).addForecast("Atlético", 1.4f ,questions.get(0));
			questions.get(1).addForecast("Athletic", 1.8f ,questions.get(1));
			questions.get(1).addForecast("Atlético", 1.2f ,questions.get(1));

			persistQuestions(questions);
			persistEvents(events);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private List<Event> createEvents(List<String> eventNames, int year, int month) {
		List<Event> events = new ArrayList<>();

		for(int i = 1; i < eventNames.size(); i++){
			events.add(getIndividualEvent(eventNames, year, month, i));
		}

		return events;
	}

	private Event getIndividualEvent(List<String> eventNames, int year, int month, int i) {
		Event event = null;
		if(i <= 10){
			event = new Event(i, eventNames.get(i), UtilDate.newDate(year, month, 17));
		}
		if(i >= 11 && i <= 16){
			event = new Event(i, eventNames.get(i), UtilDate.newDate(year, month, 1));
		}
		if(i >= 17 && i <= 20){
			event = new Event(i, eventNames.get(i), UtilDate.newDate(year, month +1, 28));
		}
		return event;
	}

	private List<Question> createQuestionsByLanguaje(List<String>[] questionNames, List<Event> events) {
		switch (Locale.getDefault().toString()){
			case "es_ES":
				return createQuestion(questionNames[0], events);
			case "en_US":
				return createQuestion(questionNames[1], events);
			case "eu_ES":
				return createQuestion(questionNames[2], events);
			default:
				return createQuestion(questionNames[0], events);
		}
	}

	private List<Question> createQuestion(List<String> questionNames, List<Event> events){
		List<Question> questions = new ArrayList<>();

		questions.add(events.get(0).addQuestion(questionNames.get(0), 1));
		questions.add(events.get(0).addQuestion(questionNames.get(1), 2));
		questions.add(events.get(10).addQuestion(questionNames.get(0), 1));
		questions.add(events.get(10).addQuestion(questionNames.get(2), 2));
		questions.add(events.get(16).addQuestion(questionNames.get(0), 1));
		questions.add(events.get(16).addQuestion(questionNames.get(3), 2));

		return questions;
	}

	private static void persistQuestions(List<Question> questions) {
		for(Question q: questions){
			db.persist(q);
		}
	}

	private static void persistEvents(List<Event> events) {
		for(Event ev: events){
			db.persist(ev);
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) {
				System.err.println(">> DataAccess: createQuestion=> error QuestionAlreadyExist: " + question + " already exists");
				throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			}
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			db.persist(ev); 
			db.getTransaction().commit();
			return q;
		
	}
	


	/**
	 * This method retrieves a question identified by its number from the database 
	 * @param questionNumber number of the question we want to retrieve
	 * @return the question
	 * @throws QuestionDoesntExist if the question does not exist in the database
	 */
	public  Question getQuestion (Integer questionNumber) throws QuestionDoesntExist {
		System.out.println(">> DataAccess: getQuestion => questionNumber="+questionNumber);
		Question q = db.find(Question.class, questionNumber);
		if(q==null) {
			System.err.println(">> DataAccess: getQuestion => error QuestionDoesntExist: No existe una pregunta con este identificador: " + questionNumber);
			throw new QuestionDoesntExist("No existe una pregunta con este identificador: " + questionNumber);
		}
		return q;
		
	}
	
	/**
	 * This method creates an event with a description and a date
	 * @param description description of the event
	 * @param eventDate date of the event
	 * @return the created event
	 * @throws EventAlreadyExist if the event already exists in the database
	 */
   public Event createEvent(String description,Date eventDate) throws EventAlreadyExist{
	   System.out.println(">> DataAccess: createEvent => description="+description+" eventDate="+eventDate);
	   ArrayList<String> a = new ArrayList<>(getEvents2(eventDate));
	   Event event;
	   if (! a.contains(description.trim().replace(" ","").toLowerCase())) {
		   db.getTransaction().begin();
		   event = new Event(description, eventDate);
		   db.persist(event);
		   db.getTransaction().commit();
		   return event;
	   }else {
		   System.err.println(">> DataAccess: createEvent => error EventAlreadyExist: "+ description + " already exists");
		   throw new EventAlreadyExist (description+" already exists!");
	   }	   
   }

   /**
	* This method creates a user
	* @User object not in db
	* @return the created user
	* @throws UserAlreadyExist if the user already exists in the database
    */
   @Override
	public User createUserInDB(User user) throws UserAlreadyExist {
		System.out.println(">> DataAccess: createUser => username="+user.getUsername()+" dni="+user.getDni()+" name="+user.getName()
				+" apellido="+user.getApellido()+" isAdmin="+user.isAdmin());
		   User userInDB = db.find(User.class, user.getDni());
		   if (userInDB==null ) {
			   db.getTransaction().begin();
			   userInDB = new User(user.getUsername(), user.getPasswd(), user.getDni(), user.getName(), user.getApellido(), user.isAdmin());
			   db.persist(userInDB);
			   db.getTransaction().commit();
		   }else {
			   System.err.println(">> DataAccess: createUser => error UserAlreadyExist: "+ userInDB.toString() + " already exists!");
			   throw new UserAlreadyExist(userInDB.toString() + " already exists!");
		   }
		   return userInDB;
	}
	
	/**
	 * This method creates a forecast for a question
	 * @param description description of the forecast
	 * @param gain gain of the forecast
	 * @param questionNumber number of the question to which the forecast is added
	 * @return the created forecast
	 * @throws ForecastAlreadyExist if the forecast already exists in the database
	 * @throws QuestionDoesntExist if the question does not exist in the database
	 */
	public Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
		  System.out.println(">> DataAccess: createForecast => description="+description+" gain="+gain+" Question="+questionNumber);
		  if(description.isEmpty()) {
			  System.err.println(">> DataAccess: createForecast => error DescriptionDoesntExist: La descripción no puede estar vacía");
	 		  throw new DescriptionDoesntExist("La descripción no puede estar vacía");
		  }
	 	  Question ques = db.find(Question.class, questionNumber);
		  if (ques == null) {
			System.err.println(">> DataAccess: createForecast => error QuestionDoesntExist: No existe una pregunta con este identificador: " + questionNumber);
			throw new QuestionDoesntExist("No existe una pregunta con este identificador: " + questionNumber);
		  }
		  if (ques.DoesForecastExists(description)) {
	 		  System.err.println(">> DataAccess: createForecast => error ForecastAlreadyExist: Esta predicción ya existe");
	 		  throw new ForecastAlreadyExist("Esta predicción ya existe");
	 	  }
	 	    db.getTransaction().begin();
			Forecast f = ques.addForecast(description, gain, ques);
			//db.persist(f);
			db.persist(ques); // db.persist(f) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return f;
	    }
	
	/**
	 * This method retrieves a user identified by its dni from the database 
	 * @param Dni dni of the user we want to retrieve
	 * @return the user
	 * @throws UserDoesntExist if the user does not exist in the database
	 */
	public User getUser(String Dni) throws UserDoesntExist{
		System.out.println(">> DataAccess: getUser => Dni="+Dni);
		User u = db.find(User.class, Dni);
		if(u==null) {
			System.err.println(">> DataAccess: getUser => error UserDoesntExist: No existe un usuario con este DNI "+Dni);
			throw new UserDoesntExist("No existe un usuario con este DNI "+Dni);
		}
		return u;
	}
	
	/**
	 * This method retrieves all the users from the database 
	 * @return a vector with all the users
	 */
	public Vector<User> getAllUsers(){
		System.out.println(">> DataAccess: getAllUsers");
		Vector<User> res = new Vector<User>();	
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u",User.class);
		List<User> users = query.getResultList();
		for(User u: users) {
			res.add(u);
		}
		return res;
	}
	
	
	/**
	 * This method assigns a result to a question
	 * @param questionNumber number of the question to which the result is assigned
	 * @param forecastNumber number of the forecast that is the result
	 * @throws QuestionDoesntExist if the question does not exist in the database
	 * @throws ForecastDoesntExist if the forecast does not exist in the database
	 */
	public void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist {
		System.out.println(">> DataAccess: assignResult => questionNumber="+questionNumber+" forecastNumber="+forecastNumber);
		Question q = db.find(Question.class, questionNumber);
		Forecast f = db.find(Forecast.class, forecastNumber);
		if(q==null) {
			System.err.println(">> DataAccess: assignResult => error QuestionDoesntExist: No existe una pregunta con este identificador " + questionNumber);
			throw new QuestionDoesntExist("No existe una pregunta con este identificador " + questionNumber);
		}
		if(f==null) {
			System.err.println(">> DataAccess: assignResult => error ForecastDoesntExist: No existe un pronostico con este identificador " + forecastNumber);
			throw new ForecastDoesntExist("No existe un pronostico con este identificador " + forecastNumber);
		}
		db.getTransaction().begin();
		q.setResult(f);
		db.getTransaction().commit();
	}
	
	/**
	 * This method removes a user from the database
	 * @param dni dni of the user we want to remove
	 * @return true if the user has been removed successfully, false otherwise
	 */
	public boolean removeUser(String dni) {
		System.out.println(">> DataAccess: removeUser => " + dni);
		User u = db.find(User.class, dni);
		if(u==null) {
			return false;
		}else {
			db.getTransaction().begin();
			db.remove(u);
			db.getTransaction().commit();
			return true;
		}
	}
	
	/**
	 * This method creates a bet for a user and a forecast
	 * @param dni dni of the user
	 * @param betMoney amount of money that the user bets
	 * @param forecastNumber number of the forecast to which the user bets
	 * @return the created bet
	 * @throws BetAlreadyExist if the bet already exists in the database
	 * @throws UserDoesntExist if the user does not exist in the database
	 * @throws ForecastDoesntExist if the forecast does not exist in the database
	 */
	public Bet createBet (String dni, float betMoney, int forecastNumber) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist {
		System.out.println(">> DataAccess: createBet => user=" + dni + " dinero apostado="+betMoney + " al forecast=" + forecastNumber);

		User u = getUserFromDB(dni);
		Forecast forecast = getForecastFromDB(forecastNumber);
		doesBetAlreadyExist(forecastNumber, u);

		db.getTransaction().begin();
 	    Bet b = u.addBet(betMoney, forecast);
 	    db.persist(u);
 		db.getTransaction().commit();
		return b;
	}

	private static void doesBetAlreadyExist(int forecastNumber, User u) throws BetAlreadyExist {
		if ( u.DoesBetExists(forecastNumber) != null) {
			System.err.println(">> DataAccess: createBet => error BetAlreadyExist: Ya existe una apuesta a este pronostico");
			throw new BetAlreadyExist("Ya existe una apuesta a este pronostico");
		}
	}

	private static Forecast getForecastFromDB(int forecastNumber) throws ForecastDoesntExist {
		Forecast forecast = db.find(Forecast.class, forecastNumber);
		if (forecast == null) {
			System.err.println(">> DataAccess: createBet => error ForecastDoesntExist: No hay un pronostico con este identificador en la base de datos, forecastNumber="+ forecastNumber);
			throw new ForecastDoesntExist("No hay un pronostico con este identificador en la base de datos, forecastNumber="+ forecastNumber);
		}
		return forecast;
	}

	private static User getUserFromDB(String dni) throws UserDoesntExist {
		User u = db.find(User.class, dni);
		if(u==null) {
			System.err.println(">> DataAccess: createBet => error UserDoesntExist: No hay un usuario con este DNI en la base de datos, dni="+ dni);
			throw new UserDoesntExist("No hay un usuario con este DNI en la base de datos, dni="+ dni);
		}
		return u;
	}

	/**
	 * This method modifies the bet Money of a bet
	 * @param betMoney the amount of money that the user bets
	 * @param betNumber number of the bet to modify
	 * @param dni dni of the user
	 * @return BetDoesntExist if the bet does not exist in the database, current Bet if the bet is not possible
	 * @throws UserDoesntExist if the user does not exist in the database**/

	public Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {

		checkIfDNIAndBetNumberRight(betNumber, dni);

		Bet bet = db.find(Bet.class, betNumber);
		User user = db.find(User.class, dni);

		checkIfUserAndBetInDB(user, bet);

		modifyBetIfPossible(betMoney, bet, user);

		return bet;
	}
	private void checkIfDNIAndBetNumberRight(int betNumber, String dni) throws UserDoesntExist, BetDoesntExist {
		checkIfDNIRight(dni);
		checkIfBetNumberRight(betNumber);
	}
	private void checkIfBetNumberRight(int betNumber) throws BetDoesntExist {
		if(betNumber < 0){
			System.err.println(">> DataAccess: modifyBet => error BetDoesntExist: error, identificador negativo");
			throw new BetDoesntExist("No existe la apuesta a modificar");
		}
	}
	private void checkIfDNIRight(String dni) throws UserDoesntExist {
		if(dni == null){
			System.err.println(">> DataAccess: modifyBet => error UserDoesntExist: error, dni nulo");
			throw new UserDoesntExist("El usuario introducido no es correcto");
		}
	}
	private void checkIfUserAndBetInDB(User user, Bet bet) throws UserDoesntExist, BetDoesntExist {
		checkIfUserInDB(user);
		checkIfBetInDB(bet);
	}
	private void checkIfUserInDB(User user) throws UserDoesntExist {
		if (user == null) {
			System.err.println(">> DataAccess: modifyBet => error UserDoesntExist: No existe un usuario con este DNI en la base de datos");
			throw new UserDoesntExist("No existe un usuario con este DNI en la base de datos");
		}
	}
	private void checkIfBetInDB(Bet bet) throws BetDoesntExist {
		if (bet == null) {
			System.err.println(">> DataAccess: modifyBet => error BetDoesntExist: No existe la apuesta ha modificar");
			throw new BetDoesntExist("No existe la apuesta a modificar");
		}
	}
	private void modifyBetIfPossible(float betMoney, Bet bet, User user) {
		double betMoneyBefore = bet.getBetMoney();
		double betMoneyAfter = betMoney + betMoneyBefore;
		float userMoney = user.getSaldo();

		if(betPossible(betMoney, userMoney, betMoneyAfter)){

			modifyBetValues(betMoney, bet, user, userMoney, (float) betMoneyAfter);

			persistModifiedBetAndUserIntoDB(bet, user);
		}
	}
	private boolean betPossible(float betMoney, float userMoney, double betMoneyAfter) {
		return userMoney >= betMoney && betMoneyAfter > 0;
	}
	private void modifyBetValues(float betMoney, Bet bet, User user, float userMoney, float betMoneyAfter) {
		user.setSaldo(userMoney - betMoney);
		bet.setBetMoney(betMoneyAfter);
	}
	private static void persistModifiedBetAndUserIntoDB(Bet bet, User user) {
		db.getTransaction().begin();
		db.persist(user);
		db.persist(bet);
		db.getTransaction().commit();
	}



	/**
	 * Este método nos permite cambiar el nombre de un usuario.
	 * @param user, el usuario que se quiere cambiar
	 * @param nombre, el nuevo nombre que va a tener el usuario
	 */
	public void modifyUserName (User user, String nombre) {
		
		User user2 = db.find(User.class, user);
		db.getTransaction().begin();
		user2.setName(nombre);
		db.persist(user2);
		db.getTransaction().commit();


	}

	/**
	 * Este método nos permite cambiar el apellido de un usuario
	 * @param user, el usuario al que se le va a cambiar el apellido
	 * @param apellido, el nuevo apellido que va a tener el usuario.
	 */
	public void modifyUserApellido (User user, String apellido) {
		
		User user2 = db.find(User.class, user);
		db.getTransaction().begin();
		user2.setApellido(apellido);
		db.persist(user2);
		db.getTransaction().commit();
		
		
	}
	
	
	/**
	  * Este método nos permite cambiar el nombre de usuario de un usuario
	 * @param user, el usuario al que se le va a cambiar el usuario
	 * @param usuario, el nuevo nombre de usuario que va a tener el usuario.
	 */
	public void modifyUserUsuario (User user, String usuario) {
		User user2 = db.find(User.class, user);
		db.getTransaction().begin();
		user2.setUsername(usuario);
		db.persist(user2);
		db.getTransaction().commit();
		
	}
	
	/**
	 * Este método nos permite cambiar la contraseña de un usuario
	 * @param user, el usuario al que se le va a cambiar el apellido
	 * @param passwd, la nueva contraseña que va a tener el usuario.
	 */
	public void modifyUserPasswd (User user, String passwd) {
		User user2 = db.find(User.class, user);
		db.getTransaction().begin();
		user2.setPasswd(passwd);
		db.persist(user2);
		db.getTransaction().commit();
		
	}

	/**
	 * Este método nos permite cambiar la tajeta de crédito de un usuario.
	 * @param dni, el dni del usuario al que se le va a cambiar la tarjeta de crédito.
	 * @param newCard, la nueva tarjeta de crédito del usuario. 
	 */
	public void modifyUserCreditCard (String dni, Long newCard) {
		User user2 = db.find(User.class, dni);
		db.getTransaction().begin();
		user2.setCreditCard(newCard);
		db.persist(user2);
		db.getTransaction().commit();
		
	}

	
	/**
	 * Este método nos permite eliminar una apuesta
	 * @param betNumber, el número de la apuesta a eliminar
	 * @throws BetDoesntExist, si no existe esa apuesta no se puede eliminar
	 */
	public void removeBet(Integer betNumber) throws BetDoesntExist{
		System.out.println(">> DataAccess: removeBet => betNumber="+betNumber);
		Bet b = db.find(Bet.class, betNumber);
		if(b==null) {
			System.err.println(">> DataAccess: removeBet => error BetDoesntExist: No hay una apuesta con este identificador "+betNumber);
			throw new BetDoesntExist("No hay una apuesta con este identificador "+betNumber);
		}
		db.getTransaction().begin();
		db.remove(b);
		b.getUser().removeBet(b.getBetNumber());
		b.getUser().setSaldo((float) (b.getUser().getSaldo() + b.getBetMoney()));
		db.getTransaction().commit();
	}
 
	
	
	/**
	 * Este método nos permite modificar el saldo de un usuario.
	 * @param saldo, el saldo a añadir
	 * @param user, el usuario al que se le va a modificar el saldo. 
	 * @return devuelve el usuario. 
	 */
	public User modifySaldo (float saldo, String user) {
		System.out.println(">> DataAccess: modifySaldo => user="+user+" saldo a añadir="+saldo);
		User user2 = db.find(User.class, user);
		db.getTransaction().begin();
		user2.setSaldo(user2.getSaldo() + saldo);
		db.getTransaction().commit();
		return user2;
	}




	/**
	 * Este método nos devuelve un pronóstico.
	 * @param forecastNumber, el número del pronóstico a devolver.
	 * @return devuelve un forecast.
	 * @throws ForecastDoesntExist, si no existe ese forecast no lo puede devolver.
	 */
	public Forecast getForecast(Integer forecastNumber) throws ForecastDoesntExist{
		System.out.println(">> DataAccess: getForecast => forecastNumber="+forecastNumber);
		Forecast f = db.find(Forecast.class, forecastNumber);
		if(f==null) {
			System.err.println(">> DataAccess: getForecast => error ForecastDoesntExist: No existe un pronostico con este identificador"+forecastNumber);
			throw new ForecastDoesntExist("No existe un pronostico con este identificador " + forecastNumber);
		}
		return f;
	}
	
	
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}




	public Vector<String> getEvents2(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<String> res = new Vector<String>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
		   res.add(ev.toString2().trim().replace(" ","").toLowerCase());
		  }
	 	return res;
	}
	




	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}

	@Override
	public boolean existQuestion(Event event, String question) {
		return false;
	}


	public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}

	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	@Override
	public void emptyDatabase() {

		try{

			Path pathArchivo1 = Paths.get(c.getDbFilename());
			Path pathArchivo2 = Paths.get(c.getDbFilename()+"$");

			Files.delete(pathArchivo1);
			Files.delete(pathArchivo2);

		}catch (IOException e){
			System.err.println("Error al eliminar archivos");
		}

	}

}
