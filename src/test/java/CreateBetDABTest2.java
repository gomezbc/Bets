

import dataAccess.DataAccessCreateBet;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.User;
import exceptions.*;
import org.junit.Test;
import test.dataAccess.TestDataAccess;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class CreateBetDABTest2 {

    // sut:system under test
    static DataAccessCreateBet sut = new DataAccessCreateBet();

    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private Event ev;
    private Forecast f;
    private User u;

    @Test
    // sut.createBet: The bet is instantiated correctly
    public void test1() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 2F;
        String forecastText = "forecast1";
        float gain = 1.2F;
        String dni = "12345678N";
        Bet bet;
        float betMoney = 1.5F;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate = null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        // configure the state of the system (create object in the database)
        try {
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText, oneDate, queryText, betMinimum);
            u = sut.createUserInDB(new User("Juan", "1234", dni, "Juan", "Lopez", false));
            f = sut.createForecast(forecastText, gain, ev.getQuestions().get(0).getQuestionNumber());
        } catch (ForecastAlreadyExist | QuestionDoesntExist e) {
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get()
                    .getForecasts().stream().filter(f -> f.getDescription().equals(forecastText)).findFirst().get();
        } catch (UserAlreadyExist e) {
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser(dni);
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        } catch (DescriptionDoesntExist e) {
            fail();
        } finally {
            testDA.close();
        }

        Bet expectedBet = new Bet(u, betMoney, f);

        // invoke System Under Test (sut)
        try {
            bet = sut.createBet(u.getDni(), betMoney, f.getForecastNumber());

            expectedBet.setBetNumber(bet.getBetNumber());
            // verify the results
            assertEquals(expectedBet, bet);
        } catch (BetAlreadyExist | ForecastDoesntExist | UserDoesntExist e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }


    @Test
    // sut.createBet: Dni is not valid
    public void test4() {

        try {
            sut.createBet("1234", 2.4F, 1);
            // it should raise an exception
            fail();
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            // we don't need to check this exceptions
        } catch (InvalidParameterException e) {
            // it should throw an exception because the dni is not valid
            assertTrue(true);
        }
        fail("The program doesn't check if the dni is valid");
    }


    @Test
    // sut.createBet: The betMoney is less than the minimum of the question
    public void test6() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 1F;
        String forecastText = "forecast1";
        float gain = 1.2F;
        String dni = "12345678N";
        float betMoney = 0.4F;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate = null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        // configure the state of the system (create object in the database)
        try {
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText, oneDate, queryText, betMinimum);
            u = sut.createUserInDB(new User("Juan", "1234", dni, "Juan", "Lopez", false));
            f = sut.createForecast(forecastText, gain, ev.getQuestions().get(0).getQuestionNumber());
        } catch (ForecastAlreadyExist | QuestionDoesntExist e) {
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get()
                    .getForecasts().stream().filter(f -> f.getDescription().equals(forecastText)).findFirst().get();
        } catch (UserAlreadyExist e) {
            try {
                // if the user already exists retrieve it from the database
                u = sut.getUser(dni);
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        } catch (DescriptionDoesntExist e) {
            fail();
        } finally {
            testDA.close();
        }

        // invoke System Under Test (sut)
        try {
            sut.createBet(u.getDni(), betMoney, f.getForecastNumber());
        } catch (Error e) {
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
        // if the program continues fail (it should raise an exception because the
        // betMoney is less than the minimum of the question)
        fail("The program doesn't check if the betMoney is less than the minimum of the question");
    }

    
}
