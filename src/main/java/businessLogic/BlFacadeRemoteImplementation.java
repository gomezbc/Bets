package businessLogic;

import domain.*;
import exceptions.*;
import iterators.ExtendedIterator;

import javax.xml.ws.Service;
import java.util.Date;
import java.util.Vector;

public class BlFacadeRemoteImplementation implements BLFacade{

    BLFacade blf;

    public BlFacadeRemoteImplementation(Service service){
        blf = service.getPort(BLFacade.class);
    }

    @Override
    public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist {
        return blf.createQuestion(event, question, betMinimum);
    }

    @Override
    public Vector<Event> getEvents(Date date) {
        return blf.getEvents(date);
    }

    @Override
    public Vector<Date> getEventsMonth(Date date) {
        return blf.getEventsMonth(date);
    }

    @Override
    public void initializeBD() {
        blf.initializeBD();
    }

    @Override
    public User createUser(User user) throws UserAlreadyExist {
        return blf.createUser(user);
    }

    @Override
    public Event createEvent(String description, Date eventDate) throws EventAlreadyExist {
        return blf.createEvent(description, eventDate);
    }

    @Override
    public Forecast createForecast(String description, float gain, int questionNumber) throws ForecastAlreadyExist, QuestionDoesntExist {
        return blf.createForecast(description, gain, questionNumber);
    }

    @Override
    public User getUser(String Dni) throws UserDoesntExist {
        return blf.getUser(Dni);
    }

    @Override
    public Question getQuestion(Integer questionNumber) throws QuestionDoesntExist {
        return blf.getQuestion(questionNumber);
    }

    @Override
    public void assignResult(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished {
        blf.assignResult(questionNumber, forecastNumber);
    }

    @Override
    public Vector<User> getAllUsers() {
        return blf.getAllUsers();
    }

    @Override
    public boolean removeUser(String dni) {
        return blf.removeUser(dni);
    }

    @Override
    public Bet createBet(String dni, float betMoney, int forecastNumber) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist {
        return blf.createBet(dni, betMoney, forecastNumber);
    }

    @Override
    public User modifySaldo(float saldo, String user2) {
        return blf.modifySaldo(saldo, user2);
    }

    @Override
    public Forecast getForecast(Integer forecastNumber) throws ForecastDoesntExist {
        return blf.getForecast(forecastNumber);
    }

    @Override
    public void updateCloseEvent(Integer numResultado) {
        blf.updateCloseEvent(numResultado);
    }

    @Override
    public void removeBet(Integer betNumber) throws BetDoesntExist {
        blf.removeBet(betNumber);
    }

    @Override
    public Bet modifyBet(float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {
        return blf.modifyBet(betMoney, betNumber, dni);
    }

    @Override
    public void modifyUserName(User user, String Nombre2) {
        blf.modifyUserName(user, Nombre2);
    }

    @Override
    public void modifyUserApellido(User user, String Apellido) {
        blf.modifyUserApellido(user, Apellido);
    }

    @Override
    public void modifyUserUsuario(User user, String Usuario) {
        blf.modifyUserUsuario(user, Usuario);
    }

    @Override
    public void modifyUserPasswd(User user, String passwd) {
        blf.modifyUserPasswd(user, passwd);
    }

    @Override
    public void modifyUserCreditCard(String user, Long newCard) {
        blf.modifyUserCreditCard(user, newCard);
    }

    @Override
    public ExtendedIterator<Event> getEventsIterator(Date date) {
        return blf.getEventsIterator(date);
    }
}
