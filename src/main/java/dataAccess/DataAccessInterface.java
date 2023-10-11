package dataAccess;

import java.util.Date;
import java.util.Vector;

import domain.*;
import exceptions.*;

public interface DataAccessInterface {


    /**
     * This method opens the database
     */
    void open(boolean initializeMode);

    /**
     * This method closes the database
     */
    void close();


    /**
     * This method removes all the elements of the database
     */
    void emptyDatabase();


    /**
     * This is the data access method that initializes the database with some events and questions.
     * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
     */
    void initializeDB();

    /**
     * This method creates a question for an event, with a question text and the minimum bet
     *
     * @param event      to which question is added
     * @param question   text of the question
     * @param betMinimum minimum quantity of the bet
     * @return the created question, or null, or an exception
     * @throws QuestionAlreadyExist if the same question already exists for the event
     */
    Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist;

    /**
     * This method retrieves a question identified by its number from the database
     *
     * @param questionNumber number of the question we want to retrieve
     * @return the question
     * @throws QuestionDoesntExist if the question does not exist in the database
     */
    Question getQuestion(Integer questionNumber) throws QuestionDoesntExist;

    /**
     * This method creates an event with a description and a date
     *
     * @param description description of the event
     * @param eventDate   date of the event
     * @return the created event
     * @throws EventAlreadyExist if the event already exists in the database
     */
    Event createEvent(String description, Date eventDate) throws EventAlreadyExist;

    /**
     * This method retrieves from the database the events of a given date
     *
     * @param date in which events are retrieved
     * @return collection of events
     */
    Vector<Event> getEvents(Date date);

    Vector<String> getEvents2(Date date);

    /**
     * This method retrieves from the database the dates a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    Vector<Date> getEventsMonth(Date date);


    /**
     * This method checks if the question has been previously added to the event
     *
     * @param event    the event
     * @param question the question to check
     * @return true if the event contains this the questions, false in other case
     */
    boolean existQuestion(Event event, String question);

    /**
     * This method creates a user
     *
     * @param username username of the user
     * @param passwd   password of the user
     * @param dni      dni of the user (unique)
     * @param name     name of the user
     * @param apellido apellido of the user
     * @param isAdmin  if the user is admin or not
     * @return the created user
     * @throws UserAlreadyExist if the user already exists in the database
     */
    User createUser(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) throws UserAlreadyExist;

    /**
     * This method retrieves a user identified by its dni from the database
     * @param Dni dni of the user we want to retrieve
     * @return the user
     * @throws UserDoesntExist if the user does not exist in the database
     */
    User getUser(String Dni) throws UserDoesntExist;

    /**
     * This method retrieves all the users from the database
     * @return a vector with all the users
     */
    Vector<User> getAllUsers();

    /**
     * Este método nos permite cambiar el nombre de un usuario.
     * @param user, el usuario que se quiere cambiar
     * @param nombre, el nuevo nombre que va a tener el usuario
     */
    void modifyUserName(User user, String nombre);

    /**
     * Este método nos permite cambiar el apellido de un usuario
     * @param user, el usuario al que se le va a cambiar el apellido
     * @param apellido, el nuevo apellido que va a tener el usuario.
     */
    void modifyUserApellido(User user, String apellido);

    /**
     * Este método nos permite cambiar el nombre de usuario de un usuario
     * @param user, el usuario al que se le va a cambiar el usuario
     * @param usuario, el nuevo nombre de usuario que va a tener el usuario.
     */
    void modifyUserUsuario(User user, String usuario);

    /**
     * Este método nos permite cambiar la contraseña de un usuario
     * @param user, el usuario al que se le va a cambiar el apellido
     * @param passwd, la nueva contraseña que va a tener el usuario.
     */
    void modifyUserPasswd(User user, String passwd);

    /**
     * Este método nos permite cambiar la tajeta de crédito de un usuario.
     * @param dni, el dni del usuario al que se le va a cambiar la tarjeta de crédito.
     * @param newCard, la nueva tarjeta de crédito del usuario.
     */
    void modifyUserCreditCard(String dni, Long newCard);

    /**
     * Este método nos permite modificar el saldo de un usuario.
     * @param saldo, el saldo a añadir
     * @param user, el usuario al que se le va a modificar el saldo.
     * @return devuelve el usuario.
     */
    User modifySaldo(float saldo, String user);

    /**
     * This method removes a user from the database
     * @param dni dni of the user we want to remove
     * @return true if the user has been removed successfully, false otherwise
     */
    boolean removeUser(String dni);

    /**
     * This method creates a forecast for a question
     * @param description description of the forecast
     * @param gain gain of the forecast
     * @param questionNumber number of the question to which the forecast is added
     * @return the created forecast
     * @throws ForecastAlreadyExist if the forecast already exists in the database
     * @throws QuestionDoesntExist if the question does not exist in the database
     */
    Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist;

    /**
     * This method creates a bet for a user and a forecast
     * @param user dni of the user
     * @param betMoney amount of money that the user bets
     * @param forecastNumber number of the forecast to which the user bets
     * @return the created bet
     * @throws BetAlreadyExist if the bet already exists in the database
     * @throws UserDoesntExist if the user does not exist in the database
     * @throws ForecastDoesntExist if the forecast does not exist in the database
     */
    Bet createBet (String user, float betMoney, int forecastNumber) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist;

    /**
     * Este método nos devuelve un pronóstico.
     * @param forecastNumber, el número del pronóstico a devolver.
     * @return devuelve un forecast.
     * @throws ForecastDoesntExist, si no existe ese forecast no lo puede devolver.
     */
    Forecast getForecast(Integer forecastNumber) throws ForecastDoesntExist;

    /**
     * This method modifies the betModey of a bet
     * @param betMoney the amount of money that the user bets
     * @param betNumber number of the bet to modify
     * @param dni dni of the user
     * @return BetDoesntExist if the bet does not exist in the database
     * @throws UserDoesntExist if the user does not exist in the database
     */
    Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist;

    /**
     * Este método nos permite eliminar una apuesta
     * @param betNumber, el número de la apuesta a eliminar
     * @throws BetDoesntExist, si no existe esa apuesta no se puede eliminar
     */
    void removeBet(Integer betNumber) throws BetDoesntExist;

    /**
     * This method assigns a result to a question
     * @param questionNumber number of the question to which the result is assigned
     * @param forecastNumber number of the forecast that is the result
     * @throws QuestionDoesntExist if the question does not exist in the database
     * @throws ForecastDoesntExist if the forecast does not exist in the database
     */
    void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist;




}