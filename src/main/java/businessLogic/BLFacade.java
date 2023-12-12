package businessLogic;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
import iterators.ExtendedIterator;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 *
	 * @param question question to save
	 * @return the saved question
	 * @throws EventFinished        if current date is after date of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question saveQuestion(Question question) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date
	 *
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public List<Event> getEventsByDate(LocalDate date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod public List<LocalDate> getDatesWithEventsInAMonth(LocalDate date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * This method invokes the data access to create a new user for the application
	 * @param user user to save
	 * @return saved user
	 * @throws UserAlreadyExist if the user already exists
	 */
	@WebMethod public User saveUser(User user) throws UserAlreadyExist;
	
	/**
	 * This method invokes the data access to save an event in the database
	 *
	 * @param event event to save
	 * @return saved event
	 * @throws EventAlreadyExist if the event already exists
	 */
	@WebMethod public Event saveEvent(Event event) throws EventAlreadyExist;
	
	/**
	 * This method invokes the data access to save a forecast in the database
	 *
	 * @param forecast forecast to save
	 * @return the saved forecast
	 * @throws ForecastAlreadyExist if the forecast already exists
	 * @throws QuestionDoesntExist  if the question doesn't exist
	 */
	@WebMethod public Forecast saveForecast(Forecast forecast) throws ForecastAlreadyExist, QuestionDoesntExist;
	
	/**
	 * This method invokes the data access to get a User from the database given its dni
	 * @param dni dni of the user
	 * @return the user
	 * @throws UserDoesntExist there is no user with that dni
	 */
	@WebMethod public User getUserByDni(String dni) throws UserDoesntExist;
	
	/**
	 * This method invokes the data access to get a Question from the database
	 * @param questionNumber number of the question
	 * @return the question
	 * @throws QuestionDoesntExist if the question doesn't exist
	 */
	@WebMethod public Question getQuestionByQuestionNumber(Integer questionNumber) throws QuestionDoesntExist;

	/**
	 * This method invokes the data access to assign a result to a question
	 * @param questionNumber number of the question to assign the result
	 * @param forecastNumber number of the forecast which is the result
	 * @throws QuestionDoesntExist there is no question with that number
	 * @throws ForecastDoesntExist there is no forecast with that number
	 * @throws EventHasntFinished the event associated to the question has not finished yet
	 */
	@WebMethod public void assignResultForecastToQuestion(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished;

	/**
	 * This method invokes the data access to get all the events from the database
	 *
	 * @return a vector with all the events
	 */
	@WebMethod public List<User> getAllUsers();
	
	/**
	 * This method invokes the data access to remove a user from the database
	 * @param dni dni of the user to remove
	 * @return true if the user has been removed, false otherwise
	 */
	@WebMethod public boolean deleteUserByDni(String dni);

	/**
	 * This method invokes the data access to save a bet in the database
	 *
	 * @param bet bet to save
	 * @return the saved bet
	 * @throws BetAlreadyExist if the bet already exists
	 * @throws UserDoesntExist if the user doesn't exist
	 */
	@WebMethod public Bet saveBet(Bet bet) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist;
	
	/**
	 * This method invokes the data access to modify the balance of a user
	 *
	 * @param balanceModification amount of money to add to the user's balance (+) to add, (-) to remove
	 * @param dni     dni of the user
	 * @return the user with the modified balance
	 */
    @WebMethod public User modifyUserBalanceByDni(float balanceModification, String dni);

	/**
	 * This method invokes the data access to get a Forecast from the database given its number
	 * @param forecastNumber number of the forecast
	 * @return the forecast
	 * @throws ForecastDoesntExist there is no forecast with that number
	 */
	@WebMethod public Forecast getForecastByForecastNumber(Integer forecastNumber) throws ForecastDoesntExist;
	
	/**
	 * This method invokes the data access to get all the users from the database, and modify their balance according to the result of the event
	 * @param resultantForecastNumber number of the forecast which is the result
	 */
	@WebMethod public void updateUsersBalanceIfWinners(Integer resultantForecastNumber);
	
	/**
	 * This method invokes the data access to remove a bet from the database
	 * @param betNumber number of the bet to remove
	 * @throws BetDoesntExist there is no bet with that number
	 */
	@WebMethod public void deleteBetByBetNumber(Integer betNumber) throws BetDoesntExist;
	
	/**
	 * This method invokes the data access to modify the betMoney from a bet in the database
	 * @param betMoney the new amount of money of the bet
	 * @param betNumber number of the bet to modify
	 * @param dni dni of the user who made the bet
	 */
	@WebMethod public Bet changeBetMoney(float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist;
	
	
	
	/**
	 * This method invokes the data access to change the name of a user
	 * @param user user to modify
	 * @param newUsername new name for the user
	 */
	@WebMethod public void changeUserUsername(User user, String newUsername);

	
	/**
	 * This method invokes the data access to change the surname of a user
	 * @param user user to modify
	 * @param lastName new surname for the user
	 */
	@WebMethod public void changeUserLastName(User user, String lastName);
	
	
	/**
	 * This method invokes the data access to change the name of a user
	 * @param user user to modify
	 * @param newName new name for the user
	 */
	@WebMethod public void changeUserName(User user, String newName);
	
	
	
	/**
	 * This method invokes the data access to change the password of a user
	 * @param user user to modify
	 * @param newPassword new password for the user
	 */
	@WebMethod public void changeUserPassword(User user, String newPassword);
	
	/**
	 * This method invokes the data access to change the creditCard of a user
	 * @param user user to modify
	 * @param newCard new creditCard for the user
	 */
	@WebMethod public void changeUserCreditCard(String user, Long newCard);


    ExtendedIterator<Event> getEventsIterator(Date date);
}