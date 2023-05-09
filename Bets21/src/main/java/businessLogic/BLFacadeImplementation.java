package businessLogic;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.BetAlreadyExist;
import exceptions.BetDoesntExist;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.EventHasntFinished;
import exceptions.ForecastAlreadyExist;
import exceptions.ForecastDoesntExist;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionDoesntExist;
import exceptions.UserAlreadyExist;
import exceptions.UserDoesntExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
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
   };
   
	
  
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
    
    
    
    
    
    @WebMethod
    public Forecast createForecast(String description, float gain, Question question) throws ForecastAlreadyExist {
 	   dbManager.open(false);
 	  Forecast forecast = null;
 	   try {
 		  forecast = dbManager.createForecast(description, gain, question);
 	   }catch(ForecastAlreadyExist e) {
 		   throw e;
 	   }finally {
 		   dbManager.close();
 	   }
 	   return forecast;
    }
    
    
    
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
    
    
    
    @WebMethod
    public Vector<User> getAllUsers(){
    	dbManager.open(false);
    	Vector<User> users = dbManager.getAllUsers();
  	    dbManager.close();
  	    return users;
    }
    
    
    
    @WebMethod
    public boolean removeUser(String dni) {
    	dbManager.open(false);
    	boolean ret = dbManager.removeUser(dni);
  	    dbManager.close();
  	    return ret;
    }
    
    
    
    @WebMethod
	public Bet createBet(String user, float betMoney, Forecast forecast) throws BetAlreadyExist, UserDoesntExist{
		dbManager.open(false);
		Bet bet = null;
		try {
			bet = dbManager.createBet(user, betMoney, forecast);
		} catch (BetAlreadyExist e) {
			throw e;
		}catch (UserDoesntExist e1) {
			throw e1;
		}finally {
			dbManager.close();
		}
		return bet;
    }
    
    
    
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
    
    @WebMethod
    public void updateCloseEvent(Integer numResultado) {
    	dbManager.open(false);
    	Vector<User> users = new Vector<User>();
    	users = dbManager.getAllUsers();
    	dbManager.close();
    	for(User u: users) {
    		Bet b = u.DoesBetExists(numResultado);
    		if(b!=null) {
    			if(b.getForecast().getForecastNumber()==numResultado) {
    				this.modifySaldo( (float) ((b.getForecast().getGain() * b.getBetMoney())), u.getDni());
    			}else {
    				this.modifySaldo( (float) (- b.getBetMoney()), u.getDni()) ;
    			}
    		}
    	}

    }

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
    
	@WebMethod
	public Bet modifyBet (float betMoney, int betNumber) throws UserDoesntExist {
		dbManager.open(false);
    	Bet bet;
    	try {
    		bet = dbManager.modifyBet(betMoney, betNumber);
    	} catch ( Exception e) {
    		throw e;
    	}finally {
    		dbManager.close();
    	}
    	return bet;
	}

	

}

