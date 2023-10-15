import dataAccess.DataAccess;
import domain.Event;
import domain.Forecast;
import domain.User;
import exceptions.*;
import org.junit.Test;
import test.dataAccess.TestDataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreateBetDAWTest {

    // sut:system under test
    static DataAccess sut = new DataAccess();

    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private Event ev;
    private Forecast f;
    private User u;

    @Test
    public void test1() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 2F;

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
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
            u = new User("Jon", "1234", "12345678N", "Jon", "Garcia", false);
        } catch (ForecastAlreadyExist | QuestionDoesntExist e) {
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get()
                    .getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        } catch (DescriptionDoesntExist e) {
            fail();
        } finally {
            testDA.close();
        }

        try {
            // invoke System Under Test (sut)
            sut.createBet(u.getDni(), 2.5F, f.getForecastNumber());
            // if the program continues fail
            fail();
        } catch (UserDoesntExist e) {
            assertTrue(true);
        } catch (ForecastDoesntExist | BetAlreadyExist e) {
            fail();
        } finally {
            // Remove the created objects in the database (cascade removing)
            testDA.open();
            testDA.removeEvent(ev);
            testDA.close();
        }
    }

    @Test
    public void test2() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 2F;
        int forecastNumber = 1;

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
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);

            // loop until we find a forecast that doesn't exist
            while (testDA.existForecast(forecastNumber)) {
                forecastNumber++;
            }
        } catch (UserAlreadyExist e) {
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            testDA.close();
        }

        try {
            // invoke System Under Test (sut)
            sut.createBet(u.getDni(), 2.5F, forecastNumber);
            // if the program continues fail
            fail();
        } catch (ForecastDoesntExist e) {
            assertTrue(true);
        } catch (UserDoesntExist | BetAlreadyExist e) {
            fail();
        } finally {
            // Remove the created objects in the database (cascade removing)
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }

    @Test
    public void test3() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 2F;

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
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
        } catch (ForecastAlreadyExist | QuestionDoesntExist e) {
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get()
                    .getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        } catch (UserAlreadyExist e) {
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser("12345678N");
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
            sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());
        } catch (BetAlreadyExist | ForecastDoesntExist | UserDoesntExist e) {
            fail();
        }

        try {
            // invoke again to arise the exception
            sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());
            // if the program continues fail
            fail();
        } catch (BetAlreadyExist e) {
            assertTrue(true);
        } catch (UserDoesntExist | ForecastDoesntExist e) {
            fail();
        } finally {
            // Remove the created objects in the database (cascade removing)
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }

    @Test
    public void test4() {
        // define parameters
        String eventText = "event1";
        String queryText = "query1";
        float betMinimum = 2F;

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
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
        } catch (ForecastAlreadyExist | QuestionDoesntExist e) {
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get()
                    .getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        } catch (UserAlreadyExist e) {
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        } catch (DescriptionDoesntExist e) {
            fail();
        } finally {
            testDA.close();
        }

        try {
            // invoke System Under Test (sut)
            sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());
            assertTrue(true);
        } catch (UserDoesntExist | ForecastDoesntExist | BetAlreadyExist e) {
            fail();
        } finally {
            // Remove the created objects in the database (cascade removing)
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }
}
