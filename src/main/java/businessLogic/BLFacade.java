package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

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
	@WebMethod public User createUser(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) throws UserAlreadyExist;
	
	/**
	 * This method invokes the data access to get a Question from the database
	 * @param questionNumber number of the question
	 * @return the question
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
	@WebMethod public Event createEvent(String description,Date eventDate) throws EventAlreadyExist;
	
	/**
	 * This method invokes the data access to create a new Forecast for a Question
	 * @param description description of the forecast
	 * @param gain gain of the forecast
	 * @param questionNumber number of the question
	 * @return the created forecast
	 * @throws ForecastAlreadyExist if the forecast already exists
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
	@WebMethod public Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist;
	
	/**
	 * This method invokes the data access to get a User from the database given its DNI
	 * @param Dni dni of the user
	 * @return the user
	 * @throws UserDoesntExist there is no user with that dni
	 */
	@WebMethod public User getUser(String Dni) throws UserDoesntExist;
	
	/**
	 * This method invokes the data access to get a Question from the database
	 * @param questionNumber number of the question
	 * @return the question
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
	@WebMethod public Question getQuestion(Integer questionNumber) throws QuestionDoesntExist;

	/**
	 * This method invokes the data access to assign a result to a question
	 * @param questionNumber number of the question to assign the result
	 * @param forecastNumber number of the forecast which is the result
	 * @throws QuestionDoesntExist there is no question with that number
	 * @throws ForecastDoesntExist there is no forecast with that number
	 * @throws EventHasntFinished the event associated to the question has not finished yet
	 */
	@WebMethod public void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished;

	/**
	 * This method invokes the data access to get all the events from the database
	 * @return a vector with all the events
	 */
	@WebMethod public Vector<User> getAllUsers();
	
	/**
	 * This method invokes the data access to remove a user from the database
	 * @param dni dni of the user to remove
	 * @return true if the user has been removed, false otherwise
	 */
	@WebMethod public boolean removeUser(String dni);

	/**
	 * This method invokes the data access to create a new Bet for a Forecast by a User
	 * @param dni dni of the user
	 * @param betMoney amount of money of the bet
	 * @param forecastNumber the forecast for which the bet is created
	 * @return the created bet
	 * @throws BetAlreadyExist if the bet already exists
	 * @throws UserDoesntExist if the user doesn't exist
	 */
	@WebMethod public Bet createBet(String dni, float betMoney, int forecastNumber) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist;
	
	/**
	 * This method invokes the data access to modify the balance of a user
	 * @param saldo amount of money to add to the user's balance (+) to add, (-) to remove
	 * @param user2 dni of the user
	 * @return the user with the modified balance
	 */
    @WebMethod public User modifySaldo (float saldo, String user2);
	
	/**
	 * This method invokes the data access to get a Forecast from the database given its number
	 * @param forecastNumber number of the forecast
	 * @return the forecast
	 * @throws ForecastDoesntExist there is no forecast with that number
	 */
	@WebMethod public Forecast getForecast (Integer forecastNumber) throws ForecastDoesntExist;
	
	/**
	 * This method invokes the data access to get all the users from the database, and modify their balance according to the result of the event
	 * @param numResultado number of the forecast which is the result
	 */
	@WebMethod public void updateCloseEvent(Integer numResultado);
	
	/**
	 * This method invokes the data access to remove a bet from the database
	 * @param betNumber number of the bet to remove
	 * @throws BetDoesntExist there is no bet with that number
	 */
	@WebMethod public void removeBet(Integer betNumber) throws BetDoesntExist;
	
	/**
	 * This method invokes the data access to modify the betModey from a bet in the database
	 * @param betMoney the new amount of money of the bet
	 * @param betNumber number of the bet to modify
	 * @param user user who made the bet
	 */
	@WebMethod public Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist ;	
	
	
	
	/**
	 * Este metodo invoca al data access para modificar el nombre de un usuario.
	 * @param user, el usuario al que se le quiere modificar el nombre
	 * @param Nombre2, el nuevo nombre que se quiere poner
	 */
	@WebMethod public void modifyUserName (User user, String Nombre2);

	
	/**
	 * Este metodo invoca al data access para modificar el apellido de un usuario.
	 * @param user, el usuario al que se le quiere modificar el nombre
	 * @param Apellido, el nuevo apellido que se quiere poner
	 */
	@WebMethod public void modifyUserApellido (User user, String Apellido);
	
	
	/**
	 * Este metodo invoca al data access para modificar el nombre de usuario de un user.
	 * @param user, el usuario al que se le quiere modificar el nombre de usuario
	 * @param Usuario, el nuevo nombre de usuario que se quiere poner
	 */
	@WebMethod public void modifyUserUsuario (User user, String Usuario);
	
	
	
	/**
	 * Este metodo invoca al data access para modificar la contraseña de un user.
	 * @param user, el usuario al que se le quiere modificar la contraseña de usuario
	 * @param passwd, la nueva contraseña que se quiere poner
	 */
	@WebMethod public void modifyUserPasswd (User user, String passwd);
	
	/**
	 * Este metodo invoca al data access para modificar la tarjeta de credito
	 * @param dni dni del usuario
	 * @param newCard la nueva tarjeta de credito
	 */
	@WebMethod public void modifyUserCreditCard(String user, Long newCard);
	
	
}