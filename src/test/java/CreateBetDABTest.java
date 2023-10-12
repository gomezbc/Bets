import dataAccess.DataAccess;
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

public class CreateBetDABTest {

    //sut:system under test
    static DataAccess sut=new DataAccess();

    //additional operations needed to execute the test
    static TestDataAccess testDA=new TestDataAccess();

    private Event ev;
    private Forecast f;
    private User u;

    @Test
    //sut.createBet: The bet is instantiated correctly
    public void test1(){
        //define paramaters
        String eventText="event1";
        String queryText="query1";
        float betMinimum= 2F;
        Bet bet;


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate=null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //configure the state of the system (create object in the dabatase)
        try{
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText,oneDate,queryText, betMinimum);
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
        }catch (ForecastAlreadyExist | QuestionDoesntExist e){
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get().getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        }catch (UserAlreadyExist e){
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            testDA.close();
        }

        Bet expectedBet = new Bet(u, 1.5F, f);

        // invoke System Under Test (sut)
        try {
            bet = sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());

            expectedBet.setBetNumber(bet.getBetNumber());
            //verify the results
            assertEquals(expectedBet, bet);
        } catch (BetAlreadyExist | ForecastDoesntExist | UserDoesntExist e) {
            fail();
        }finally {
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }

    @Test
    //sut.createBet: The bet is already in the database
    public void test2(){
        //define paramaters
        String eventText="event1";
        String queryText="query1";
        float betMinimum= 2F;


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate=null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //configure the state of the system (create object in the dabatase)
        try{
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText,oneDate,queryText, betMinimum);
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
        }catch (ForecastAlreadyExist | QuestionDoesntExist e){
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get().getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        }catch (UserAlreadyExist e){
            // if the user already exists we retrieve it from the database
            try {
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            testDA.close();
        }

        // invoke System Under Test (sut)
        try {
            sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());
        } catch (BetAlreadyExist | ForecastDoesntExist | UserDoesntExist e) {
            fail();
        }

        // invoke System Under Test (sut)
        try {
            sut.createBet(u.getDni(), 1.5F, f.getForecastNumber());
            // if the program continues fail
            fail();
        } catch (BetAlreadyExist e) {
            assertTrue(true);
        } catch (UserDoesntExist | ForecastDoesntExist e) {
            fail();
        } finally {
            //Remove the created objects in the database (cascade removing)
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }

    @Test
    //sut.createBet: dni is null
    public void test3(){
        try {
            sut.createBet(null, 2.4F, 1);
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e){
            // it should throw an exception because the user is null
            System.err.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    //sut.createBet: Dni is not valid
    public void test4(){

        try {
            sut.createBet("1234", 2.4F, 1);
            // it  should raise an exception
            fail();
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            // we don't need to check this exceptions
        } catch (InvalidParameterException e){
            // it should throw an exception because the dni is not valid
            assertTrue(true);
        }
        fail("The program doesn't check if the dni is valid");
    }

    @Test
    //sut.createBet: User is not in the DB
    public void test5(){

        u = new User("Juan", "1234", "25365678N", "Juan", "Lopez", false);

        try {
            sut.createBet(u.getDni(), 2.7F, 1);
            // it  should raise an exception
            fail();
        }catch (UserDoesntExist e){
            assertTrue(true);
        } catch (BetAlreadyExist | ForecastDoesntExist e) {
            // these exception should not be raised
            fail();
        }
    }
    @Test
    //sut.createBet: The betMoney is less than the minimum of the question
    public void test6(){
        //define paramaters
        String eventText="event1";
        String queryText="query1";
        float betMinimum= 1F;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate=null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //configure the state of the system (create object in the dabatase)
        try{
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText,oneDate,queryText, betMinimum);
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
            f = sut.createForecast("forecast1", 1.2F, ev.getQuestions().get(0).getQuestionNumber());
        }catch (ForecastAlreadyExist | QuestionDoesntExist e){
            // if the forecast already exists we inherit it from the event
            f = ev.getQuestions().stream().filter(q -> q.getQuestion().equals(queryText)).findFirst().get().getForecasts().stream().filter(f -> f.getDescription().equals("forecast1")).findFirst().get();
        }catch (UserAlreadyExist e){
            try {
                // if the user already exists retrive it from the database
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            testDA.close();
        }

        // invoke System Under Test (sut)
        try {
            sut.createBet(u.getDni(), 0.4F, f.getForecastNumber());
            // if the program continues fail (it should raise an exception because the betMoney is less than the minimum of the question)
            fail("The program doesn't check if the betMoney is less than the minimum of the question");
        } catch (Exception e) {
            assertTrue(true);
        }finally {
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }

    @Test
    //sut.createBet: The forecast is not in the database
    public void test7(){
        //define paramaters
        String eventText="event1";
        String queryText="query1";
        float betMinimum= 1F;
        int forecastNumer = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date oneDate=null;
        try {
            oneDate = sdf.parse("05/10/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //configure the state of the system (create object in the dabatase)
        try{
            testDA.open();
            ev = testDA.addEventWithQuestion(eventText,oneDate,queryText, betMinimum);
            u = sut.createUser("Juan", "1234", "12345678N", "Juan", "Lopez", false);
        } catch (UserAlreadyExist e){
            try {
                // if the user already exists retrive it from the database
                u = sut.getUser("12345678N");
            } catch (UserDoesntExist ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            testDA.close();
        }

        // get a forecast number that is not in the database
        while(true){
            try{
                if(sut.getForecast(forecastNumer)!=null)
                    forecastNumer++;
            }catch (ForecastDoesntExist e) {
                break;
            }
        }

        // invoke System Under Test (sut)
        try {
            sut.createBet(u.getDni(), 2.4F, forecastNumer);
            // if the program continues fail (it should raise an exception because the betMoney is less than the minimum of the question)
            fail();
        }catch (ForecastDoesntExist e){
            assertTrue(true);
        } catch (UserDoesntExist | BetAlreadyExist e) {
            fail();
        }finally {
            testDA.open();
            testDA.removeEvent(ev);
            sut.removeUser(u.getDni());
            testDA.close();
        }
    }
}
