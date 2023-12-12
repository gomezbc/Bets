package businessLogic;

import domain.*;
import exceptions.*;
import iterators.ExtendedIterator;

import javax.xml.ws.Service;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class BlFacadeRemoteImplementation implements BLFacade{

    BLFacade blf;

    public BlFacadeRemoteImplementation(Service service){
        blf = service.getPort(BLFacade.class);
    }

    @Override
    public Question saveQuestion(Question question) throws EventFinished, QuestionAlreadyExist {
        return blf.saveQuestion(question);
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        return blf.getEventsByDate(date);
    }

    @Override
    public List<LocalDate> getDatesWithEventsInAMonth(LocalDate date) {
        return blf.getDatesWithEventsInAMonth(date);
    }

    @Override
    public void initializeBD() {
        blf.initializeBD();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExist {
        return blf.saveUser(user);
    }

    @Override
    public Event saveEvent(Event event) throws EventAlreadyExist {
        return blf.saveEvent(event);
    }

    @Override
    public Forecast saveForecast(Forecast forecast) throws ForecastAlreadyExist, QuestionDoesntExist {
        return blf.saveForecast(forecast);
    }

    @Override
    public User getUserByDni(String dni) throws UserDoesntExist {
        return blf.getUserByDni(dni);
    }

    @Override
    public Question getQuestionByQuestionNumber(Integer questionNumber) throws QuestionDoesntExist {
        return blf.getQuestionByQuestionNumber(questionNumber);
    }

    @Override
    public void assignResultForecastToQuestion(Integer questionNumber, Integer forecastNumber) throws QuestionDoesntExist, ForecastDoesntExist, EventHasntFinished {
        blf.assignResultForecastToQuestion(questionNumber, forecastNumber);
    }

    @Override
    public List<User> getAllUsers() {
        return blf.getAllUsers();
    }

    @Override
    public boolean deleteUserByDni(String dni) {
        return blf.deleteUserByDni(dni);
    }

    @Override
    public Bet saveBet(Bet bet) throws BetAlreadyExist, UserDoesntExist, ForecastDoesntExist {
        return blf.saveBet(bet);
    }

    @Override
    public User modifyUserBalanceByDni(float balanceModification, String dni) {
        return blf.modifyUserBalanceByDni(balanceModification, dni);
    }

    @Override
    public Forecast getForecastByForecastNumber(Integer forecastNumber) throws ForecastDoesntExist {
        return blf.getForecastByForecastNumber(forecastNumber);
    }

    @Override
    public void updateUsersBalanceIfWinners(Integer resultantForecastNumber) {
        blf.updateUsersBalanceIfWinners(resultantForecastNumber);
    }

    @Override
    public void deleteBetByBetNumber(Integer betNumber) throws BetDoesntExist {
        blf.deleteBetByBetNumber(betNumber);
    }

    @Override
    public Bet changeBetMoney(float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {
        return blf.changeBetMoney(betMoney, betNumber, dni);
    }

    @Override
    public void changeUserUsername(User user, String newUsername) {
        blf.changeUserUsername(user, newUsername);
    }

    @Override
    public void changeUserLastName(User user, String lastName) {
        blf.changeUserLastName(user, lastName);
    }

    @Override
    public void changeUserName(User user, String newName) {
        blf.changeUserName(user, newName);
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        blf.changeUserPassword(user, newPassword);
    }

    @Override
    public void changeUserCreditCard(String user, Long newCard) {
        blf.changeUserCreditCard(user, newCard);
    }

    @Override
    public ExtendedIterator<Event> getEventsIterator(Date date) {
        return blf.getEventsIterator(date);
    }
}
