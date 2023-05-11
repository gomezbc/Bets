package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import domain.Booking;
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
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	@WebMethod public User createUser(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) throws UserAlreadyExist;
	
	@WebMethod public Event createEvent(String description,Date eventDate) throws EventAlreadyExist;
	
	@WebMethod public Forecast createForecast(String description, float gain, Question question) throws ForecastAlreadyExist;
	
	@WebMethod public User getUser(String Dni) throws UserDoesntExist;
	
	@WebMethod public Question getQuestion(Integer questionNumber) throws QuestionDoesntExist;

	@WebMethod public void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished;

	@WebMethod public Vector<User> getAllUsers();
	
	@WebMethod public Bet createBet (String user, float betMoney, Forecast forecast) throws BetAlreadyExist, UserDoesntExist;	
	
    @WebMethod public User modifySaldo (float saldo, String user2);
    	
	@WebMethod public boolean removeUser(String dni);
	
	@WebMethod public Forecast getForecast (Integer forecastNumber) throws ForecastDoesntExist;
	
	@WebMethod public void updateCloseEvent(Integer numResultado);
	
	@WebMethod public void removeBet(Integer betNumber) throws BetDoesntExist;
	
	@WebMethod public Bet modifyBet (float betMoney, int betNumber, User user) throws UserDoesntExist ;
	
	@WebMethod public boolean bApostado (User u, int ForecastNumber);
	


}