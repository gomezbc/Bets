package dataAccess;


import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessCreateBet{
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccessCreateBet(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessCreateBet()  {
		 this(false);
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

	public User getUser(String Dni) throws UserDoesntExist{
		System.out.println(">> DataAccess: getUser => Dni="+Dni);
		User u = db.find(User.class, Dni);
		if(u==null) {
			System.err.println(">> DataAccess: getUser => error UserDoesntExist: No existe un usuario con este DNI "+Dni);
			throw new UserDoesntExist("No existe un usuario con este DNI "+Dni);
		}
		return u;
	}

	public Forecast getForecast(Integer forecastNumber) throws ForecastDoesntExist{
		System.out.println(">> DataAccess: getForecast => forecastNumber="+forecastNumber);
		Forecast f = db.find(Forecast.class, forecastNumber);
		if(f==null) {
			System.err.println(">> DataAccess: getForecast => error ForecastDoesntExist: No existe un pronostico con este identificador"+forecastNumber);
			throw new ForecastDoesntExist("No existe un pronostico con este identificador " + forecastNumber);
		}
		return f;
	}

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
		db.persist(ques);
		db.getTransaction().commit();
		return f;
	}

}
