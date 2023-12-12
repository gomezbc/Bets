package businessLogic;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import iterators.EventExtendedIterator;
import iterators.ExtendedIterator;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeLocalImplementation implements BLFacade {
	DataAccessInterface dbManager;

	static final  String DBOPENMODE = "initialize";

	public BLFacadeLocalImplementation()  {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(DBOPENMODE)) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals(DBOPENMODE));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeLocalImplementation(DataAccessInterface da)  {
		
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
     * @param question text of the question
     * @return the created question, or null, or an exception
     * @throws EventFinished        if current data is after data of the event
     * @throws QuestionAlreadyExist if the same question already exists for the event
     */
   @WebMethod
   public Question saveQuestion(Question question) throws EventFinished, QuestionAlreadyExist{
	   
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
	public List<Event> getEventsByDate(LocalDate date)  {
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
	@WebMethod public List<LocalDate> getDatesWithEventsInAMonth(LocalDate date) {
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
    public User saveUser(User user) throws UserAlreadyExist {
 	   dbManager.open(false);
 	   try {
 		   user = dbManager.createUserInDB(user);
 	   }catch(UserAlreadyExist e) {
 		   throw e;
 	   }finally {
 		   dbManager.close();
 	   }
 	   return null;
    }
    
    
    
    /**
	 * This method invokes the data access to get a Question from the database
	 * @param questionNumber number of the question
	 * @return the question
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
    @WebMethod 
	public Question getQuestionByQuestionNumber(Integer questionNumber) throws QuestionDoesntExist{
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
	 *
	 * @param event@return the created event
	 * @throws EventAlreadyExist if the event already exists
	 */
    @WebMethod
    public Event saveEvent(Event event) throws EventAlreadyExist {
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
	 *
	 * @param forecast@return the created forecast
	 * @throws ForecastAlreadyExist if the forecast already exists
	 * @throws QuestionDoesntExist  if the question doesn't exist
	 */
    @WebMethod
    public Forecast saveForecast(Forecast forecast) throws ForecastAlreadyExist, QuestionDoesntExist {
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
    public User getUserByDni(String Dni) throws UserDoesntExist {
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
    public void assignResultForecastToQuestion(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished
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
	 *
	 * @return a vector with all the events
	 */
    @WebMethod
    public List<User> getAllUsers(){
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
    public boolean deleteUserByDni(String dni) {
    	dbManager.open(false);
    	boolean ret = dbManager.removeUser(dni);
  	    dbManager.close();
  	    return ret;
    }
    
    
    
    
    /**
	 * This method invokes the data access to create a new Bet for a Forecast by a User
	 *
	 * @param bet@return the created bet
	 * @throws BetAlreadyExist if the bet already exists
	 * @throws UserDoesntExist if the user doesn't exist
	 */
    @WebMethod
	public Bet saveBet(Bet bet) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist{
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
	 *
	 * @param balanceModification amount of money to add to the user's balance (+) to add, (-) to remove
	 * @param dni     dni of the user
	 * @return the user with the modified balance
	 */
    @WebMethod
    public User modifyUserBalanceByDni(float balanceModification, String dni) {
    	dbManager.open(false);
    	User user = null;
    	try {
    		user = dbManager.modifySaldo(balanceModification, dni);
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
    public Forecast getForecastByForecastNumber(Integer forecastNumber) throws ForecastDoesntExist{
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
	 * @param resultantForecastNumber number of the forecast which is the result
	 */
    @WebMethod
    public void updateUsersBalanceIfWinners(Integer resultantForecastNumber) {
    	dbManager.open(false);
    	Vector<User> users;
    	users = dbManager.getAllUsers();
    	dbManager.close();
    	for(User u: users) {
    		Bet b = u.DoesBetExists(resultantForecastNumber);
    		if(b!=null) {
    			if(b.getForecast().getForecastNumber().equals(resultantForecastNumber)) {
    				this.modifyUserBalanceByDni( (float) (b.getForecast().getGain() * b.getBetMoney()), u.getDni());
    			}else {
    				this.modifyUserBalanceByDni( (float) (- b.getBetMoney()), u.getDni()) ;
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
	public void deleteBetByBetNumber(Integer betNumber) throws BetDoesntExist {
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
	public Bet changeBetMoney(float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {
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
	 * @param newUsername, el nuevo nombre que se quiere poner
	 */
	@WebMethod
	 public void changeUserUsername(User user, String newUsername) {
		dbManager.open(false);
		try {
			dbManager.modifyUserName(user, newUsername);
			
		} catch ( Exception e) {
			throw e;
			
		} finally {
			dbManager.close();
		}
		 
	 }
	
	
	/**
	 * Este metodo invoca al data access para modificar el apellido de un usuario.
	 * @param user, el usuario al que se le quiere modificar el nombre
	 * @param lastName, el nuevo apellido que se quiere poner
	 */
	@WebMethod
	 public void changeUserLastName(User user, String lastName) {
		dbManager.open(false);
		try {
			dbManager.modifyUserApellido(user, lastName);
			
		} catch ( Exception e) {
			throw e;
			
		} finally {
			dbManager.close();
		}
		 
	 }
	
	
	
	/**
	 * Este metodo invoca al data access para modificar el nombre de usuario de un user.
	 * @param user, el usuario al que se le quiere modificar el nombre de usuario
	 * @param newName, el nuevo nombre de usuario que se quiere poner
	 */
	 @WebMethod
	 public void changeUserName(User user, String newName) {
		 dbManager.open(false);
			try {
				dbManager.modifyUserUsuario(user, newName);
				
			} catch ( Exception e) {
				throw e;
				
			} finally {
				dbManager.close();
			}
			 
	 }
	
	 

	/**
	 * Este metodo invoca al data access para modificar la contraseña de un user.
	 * @param user, el usuario al que se le quiere modificar la contraseña de usuario
	 * @param newPassword, la nueva contraseña que se quiere poner
	 */ 
	 @WebMethod
	 public void changeUserPassword(User user, String newPassword) {
		dbManager.open(false);
			try {
				dbManager.modifyUserPasswd(user, newPassword);
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
	 public void changeUserCreditCard(String dni, Long newCard) {
		 dbManager.open(false);
			try {
				dbManager.modifyUserCreditCard(dni, newCard);
			} finally {
				dbManager.close();
			}
	 }

	@Override
	public ExtendedIterator<Event> getEventsIterator(Date date) {
		List<Event> events = getEventsByDate(date);
		return new EventExtendedIterator(events);
	}


}

