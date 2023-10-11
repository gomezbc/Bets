package dataAccess;


import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

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
import exceptions.BetAlreadyExist;
import exceptions.BetDoesntExist;
import exceptions.EventAlreadyExist;
import exceptions.ForecastAlreadyExist;
import exceptions.ForecastDoesntExist;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionDoesntExist;
import exceptions.UserAlreadyExist;
import exceptions.UserDoesntExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  implements DataAccessInterface{
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
	    
			Event ev1=new Event(1, "Atletico-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta De Vigo", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alaves-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Espanyol-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta De Vigo", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Espanyol-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				
			}
			
			q1.addForecast("Athletic", 1.5f ,q1);
			q1.addForecast("Atlético", 1.4f ,q1);
			q2.addForecast("Athletic", 1.8f ,q2);
			q2.addForecast("Atlético", 1.2f ,q2);
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 
	
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);	
			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
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
	   Vector<String> a = getEvents2(eventDate);
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
	* @param username username of the user
	* @param passwd password of the user
	* @param dni dni of the user (unique)
	* @param name name of the user
	* @param apellido apellido of the user
	* @param isAdmin if the user is admin or not
	* @return the created user
	* @throws UserAlreadyExist if the user already exists in the database
    */
	public User createUser(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) throws UserAlreadyExist {
		System.out.println(">> DataAccess: createUser => username="+username+" dni="+dni+" name="+name+" apellido="+apellido+" isAdmin="+isAdmin);
		   User user = db.find(User.class, dni);
		   if (user==null ) {
			   db.getTransaction().begin();
			   user = new User(username, passwd, dni, name, apellido, isAdmin);
			   db.persist(user);
			   db.getTransaction().commit();
		   }else {
			   System.err.println(">> DataAccess: createUser => error UserAlreadyExist: "+ user.toString() + " already exists!");
			   throw new UserAlreadyExist(user.toString() + " already exists!");
		   }
		   return user;
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
	public Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist {
		  System.out.println(">> DataAccess: createForecast => description="+description+" gain="+gain+" Question="+questionNumber);
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
		Forecast forecast = db.find(Forecast.class, forecastNumber);
		User u = db.find(User.class, dni);
		if(u==null) {
			System.err.println(">> DataAccess: createBet => error UserDoesntExist: No hay un usuario con este DNI en la base de datos, dni="+dni);
			throw new UserDoesntExist("No hay un usuario con este DNI en la base de datos, dni="+dni);
		}
		if (forecast == null) {
			System.err.println(">> DataAccess: createBet => error ForecastDoesntExist: No hay un pronostico con este identificador en la base de datos, forecastNumber="+forecastNumber);
			throw new ForecastDoesntExist("No hay un pronostico con este identificador en la base de datos, forecastNumber="+forecastNumber);
		}

		if ( u.DoesBetExists(forecastNumber) != null) {
			System.err.println(">> DataAccess: createBet => error BetAlreadyExist: Ya existe una apuesta a este pronostico");
			throw new BetAlreadyExist("Ya existe una apuesta a este pronostico");
		}
			db.getTransaction().begin();
 	    	Bet b = u.addBet(betMoney, forecast);
 	    	db.persist(u);
 			db.getTransaction().commit();
 			return b;
	}

	/**
	 * This method modifies the betModey of a bet
	 * @param betMoney the amount of money that the user bets
	 * @param betNumber number of the bet to modify
	 * @param dni dni of the user
	 * @return BetDoesntExist if the bet does not exist in the database
	 * @throws UserDoesntExist if the user does not exist in the database
	 */
	public Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {
		Bet bet = db.find(Bet.class, betNumber);
		User user2 = db.find(User.class, dni);
		if ( bet == null) {
			System.err.println(">> DataAccess: modifyBet => error BetDoesntExist: No existe la apuesta ha modificar");
			throw new BetDoesntExist("No existe la apuesta ha modificar");
		}
		if ( user2 == null) {
			System.err.println(">> DataAccess: modifyBet => error UserDoesntExist: No existe un usuario con este DNI en la base de datos, dni="+dni);
			throw new UserDoesntExist("No existe un usuario con este DNI en la base de datos, dni="+dni);
		}
			
		double betMoneyAntes = bet.getBetMoney();
		double betTotal = betMoney + betMoneyAntes;
		db.getTransaction().begin();
		if((betTotal)>0) {
			user2.setSaldo(user2.getSaldo() - betMoney);
			bet.setBetMoney((float)betTotal);
		}
		db.persist(user2);
 	    db.persist(bet);
 		db.getTransaction().commit();
 		return bet;
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
		File f=new File(c.getDbFilename());
		f.delete();
		File f2=new File(c.getDbFilename()+"$");
		f2.delete();
	}

}
