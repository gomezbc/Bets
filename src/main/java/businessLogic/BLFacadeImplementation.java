package businessLogic;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.*;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccessInterface dbManager;

	static final  String DBOPENMODE = "initialize";

	public BLFacadeImplementation()  {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(DBOPENMODE)) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals(DBOPENMODE));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccessInterface da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(DBOPENMODE)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);

		dbManager.close();
		
		return qry;
   }
   
	
  
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
	/**
	 * This method invokes the data access to create a new user for the application
	 * @param username username of the user
	 * @param passwd password of the user
	 * @param dni dni of the user
	 * @param name name of the user
	 * @param apellido apellido of the user
	 * @param isAdmin if the user is admin or not
	 * @return the created user
	 * @throws UserAlreadyExist if the user already exists
	 */
    @WebMethod
    public User createUser(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) throws UserAlreadyExist {
 	   dbManager.open(false);
 	   User user = null;
 	   try {   
 		   user = dbManager.createUser(username, passwd, dni, name, apellido, isAdmin);
 	   }catch(UserAlreadyExist e) {
 		   throw e;
 	   }finally {
 		   dbManager.close();
 	   }
 	   return user;
    }
    
    
    
    /**
	 * This method invokes the data access to get a Question from the database
	 * @param questionNumber number of the question
	 * @return the question
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
    @WebMethod 
	public Question getQuestion (Integer questionNumber) throws QuestionDoesntExist{
		dbManager.open(false);

		Question ev;
		try {
			ev = dbManager.getQuestion(questionNumber);
		} catch (QuestionDoesntExist e) {
			throw e;
		}finally {
			dbManager.close();
		}
		return ( ev ) ;
		
	}
	
    
    
    
    
    /**
	 * This method invokes the data access to create a new Event
	 * @param description description of the event
	 * @param eventDate date of the event
	 * @return the created event
	 * @throws EventAlreadyExist if the event already exists
	 */
    @WebMethod
    public Event createEvent(String description,Date eventDate) throws EventAlreadyExist {
    	dbManager.open(false);
    	Event event = null; 
    	try {
    		event = dbManager.createEvent(description, eventDate);
    	} catch (EventAlreadyExist e){
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    	return event; 
    }
    
    
    
    
    
    /**
	 * This method invokes the data access to create a new Forecast for a Question
	 * @param description description of the forecast
	 * @param gain gain of the forecast
	 * @param questionNumber number of the question
	 * @return the created forecast
	 * @throws ForecastAlreadyExist if the forecast already exists
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
    @WebMethod
    public Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist {
 	   dbManager.open(false);
 	  Forecast forecast = null;
 	   try {
 		  forecast = dbManager.createForecast(description, gain, questionNumber);
 	   }catch(ForecastAlreadyExist | QuestionDoesntExist e) {
 		   throw e;
 	   } catch (DescriptionDoesntExist e) {
           throw new RuntimeException(e);
       } finally {
 		   dbManager.close();
 	   }
 	   return forecast;
    }
    
    
    
    
    
    /**
	 * This method invokes the data access to get a User from the database given its DNI
	 * @param Dni dni of the user
	 * @return the user
	 * @throws UserDoesntExist there is no user with that dni
	 */
    @WebMethod
    public User getUser(String Dni) throws UserDoesntExist {
  	   dbManager.open(false);
  	   User u = null;
  	   try {
  		   u = dbManager.getUser(Dni);
  	   }catch(UserDoesntExist e) {
  		 throw new UserDoesntExist(e.getMessage());
  	   }finally {
  		   dbManager.close();
  	   }
 	   return u;
    }
    
    
    
    
    
    /**
	 * This method invokes the data access to assign a result to a question
	 * @param questionNumber number of the question to assign the result
	 * @param forecastNumber number of the forecast which is the result
	 * @throws QuestionDoesntExist there is no question with that number
	 * @throws ForecastDoesntExist there is no forecast with that number
	 * @throws EventHasntFinished the event associated to the question has not finished yet
	 */
    @WebMethod
    public void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished
    {
    	dbManager.open(false);
    	try {
    		Event ev = dbManager.getQuestion(questionNumber).getEvent();
    		if(new Date().compareTo(ev.getEventDate())<0) {
    			throw new EventHasntFinished(ev.getEventNumber()+" "+ev.getDescription()+" "+ev.getEventDate());
    		}
    		dbManager.assignResult(questionNumber, forecastNumber);
    	}catch(Exception e) {
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    }
    
    
    
    
    /**
	 * This method invokes the data access to get all the events from the database
	 * @return a vector with all the events
	 */
    @WebMethod
    public Vector<User> getAllUsers(){
    	dbManager.open(false);
    	Vector<User> users = dbManager.getAllUsers();
  	    dbManager.close();
  	    return users;
    }
    
    
    
    
    
    /**
	 * This method invokes the data access to remove a user from the database
	 * @param dni dni of the user to remove
	 * @return true if the user has been removed, false otherwise
	 */
    @WebMethod
    public boolean removeUser(String dni) {
    	dbManager.open(false);
    	boolean ret = dbManager.removeUser(dni);
  	    dbManager.close();
  	    return ret;
    }
    
    
    
    
    /**
	 * This method invokes the data access to create a new Bet for a Forecast by a User
	 * @param dni dni of the user
	 * @param betMoney amount of money of the bet
	 * @param forecastNumber number of the forecast for which the bet is created
	 * @return the created bet
	 * @throws BetAlreadyExist if the bet already exists
	 * @throws UserDoesntExist if the user doesn't exist
	 */
    @WebMethod
	public Bet createBet(String dni, float betMoney, int forecastNumber) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist{
		dbManager.open(false);
		Bet bet = null;
		try {
			bet = dbManager.createBet(dni, betMoney, forecastNumber);
		} catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
			throw e;
		}finally {
			dbManager.close();
		}
		return bet;
    }
    
    
    
    
    
    /**
	 * This method invokes the data access to modify the balance of a user
	 * @param saldo amount of money to add to the user's balance (+) to add, (-) to remove
	 * @param user2 dni of the user
	 * @return the user with the modified balance
	 */
    @WebMethod
    public User modifySaldo (float saldo, String user2) {
    	dbManager.open(false);
    	User user = null;
    	try {
    		user = dbManager.modifySaldo(saldo, user2);
    	} catch ( Exception e) {
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    	return user;
    }
    
    
    
    
    /**
	 * This method invokes the data access to get a Forecast from the database given its number
	 * @param forecastNumber number of the forecast
	 * @return the forecast
	 * @throws ForecastDoesntExist there is no forecast with that number
	 */
    @WebMethod 
    public Forecast getForecast (Integer forecastNumber) throws ForecastDoesntExist{
    	Forecast ret = null;
    	dbManager.open(false);
    	try {
    		    ret = dbManager.getForecast(forecastNumber);
    	}catch(ForecastDoesntExist e) {
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    	return ret;
    }
    
    
    
    
	/**
	 * This method invokes the data access to get all the users from the database, and modify their balance according to the result of the event
	 * @param numResultado number of the forecast which is the result
	 */
    @WebMethod
    public void updateCloseEvent(Integer numResultado) {
    	dbManager.open(false);
    	Vector<User> users;
    	users = dbManager.getAllUsers();
    	dbManager.close();
    	for(User u: users) {
    		Bet b = u.DoesBetExists(numResultado);
    		if(b!=null) {
    			if(b.getForecast().getForecastNumber().equals(numResultado)) {
    				this.modifySaldo( (float) (b.getForecast().getGain() * b.getBetMoney()), u.getDni());
    			}else {
    				this.modifySaldo( (float) (- b.getBetMoney()), u.getDni()) ;
    			}
    		}
    	}

    }

    
    
    
	/**
	 * This method invokes the data access to remove a bet from the database
	 * @param betNumber number of the bet to remove
	 * @throws BetDoesntExist there is no bet with that number
	 */
	@Override
	public void removeBet(Integer betNumber) throws BetDoesntExist {
		dbManager.open(false);
		try {
            dbManager.removeBet(betNumber);
		}catch(BetDoesntExist e) {
        	throw e;
        }finally {
        	dbManager.close();
        }
	}
    
	
	
	
	/**
	 * This method invokes the data access to modify the betModey from a bet in the database
	 * @param betMoney the new amount of money of the bet
	 * @param betNumber number of the bet to modify
	 * @param dni dni of the user who made the bet
	 */
	@WebMethod
	public Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {
		dbManager.open(false);
    	Bet bet;
    	try {
    		bet = dbManager.modifyBet(betMoney, betNumber, dni);
    	} catch ( Exception e) {
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    	return bet;
	}

	
	/**
	 * Este metodo invoca al data access para modificar el nombre de un usuario.
	 * @param user, el usuario al que se le quiere modificar el nombre
	 * @param Nombre2, el nuevo nombre que se quiere poner
	 */
	@WebMethod
	 public void modifyUserName (User user, String Nombre2) {
		dbManager.open(false);
		try {
			dbManager.modifyUserName(user, Nombre2);
			
		} catch ( Exception e) {
			throw e;
			
		} finally {
			dbManager.close();
		}
		 
	 }
	
	
	/**
	 * Este metodo invoca al data access para modificar el apellido de un usuario.
	 * @param user, el usuario al que se le quiere modificar el nombre
	 * @param Apellido, el nuevo apellido que se quiere poner
	 */
	@WebMethod
	 public void modifyUserApellido (User user, String Apellido) {
		dbManager.open(false);
		try {
			dbManager.modifyUserApellido(user, Apellido);
			
		} catch ( Exception e) {
			throw e;
			
		} finally {
			dbManager.close();
		}
		 
	 }
	
	
	
	/**
	 * Este metodo invoca al data access para modificar el nombre de usuario de un user.
	 * @param user, el usuario al que se le quiere modificar el nombre de usuario
	 * @param Usuario, el nuevo nombre de usuario que se quiere poner
	 */
	 @WebMethod
	 public void modifyUserUsuario (User user, String Usuario) {
		 dbManager.open(false);
			try {
				dbManager.modifyUserUsuario(user, Usuario);
				
			} catch ( Exception e) {
				throw e;
				
			} finally {
				dbManager.close();
			}
			 
	 }
	
	 

	/**
	 * Este metodo invoca al data access para modificar la contraseña de un user.
	 * @param user, el usuario al que se le quiere modificar la contraseña de usuario
	 * @param passwd, la nueva contraseña que se quiere poner
	 */ 
	 @WebMethod
	 public void modifyUserPasswd (User user, String passwd) {
		dbManager.open(false);
			try {
				dbManager.modifyUserPasswd(user, passwd);
			} finally {
				dbManager.close();
			}
	 }
	 
	 
	/**
	 * Este metodo invoca al data access para modificar la tarjeta de credito
	 * @param dni dni del usuario
	 * @param newCard la nueva tarjeta de credito
	 */
	 @WebMethod
	 public void modifyUserCreditCard(String dni, Long newCard) {
		 dbManager.open(false);
			try {
				dbManager.modifyUserCreditCard(dni, newCard);
			} finally {
				dbManager.close();
			}
	 }
	
	

}

