package test.dataAccess;

import dataAccess.DataAccess;
import domain.*;
import exceptions.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ModifyBetDAWTest {
    static DataAccess da = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();

    String dni = "12345678A";
    Event event;
    Forecast forecast;
    Bet bet;

    @Before
    public void abrirConexionBD(){
        da.open(false);
    }

    @After
    public void cleanBD(){
        removeUsr();
        removeBetAndEvent();

        da.close();
    }

    private void removeUsr() {
        da.removeUser("12345678A");
    }
    private void removeBetAndEvent() {
        try{
            if(bet != null){
                da.removeBet(bet.getBetNumber());
                testDA.removeEvent(event);
            }
        }catch (BetDoesntExist e){
            System.out.println("No existía la apuesta");
        }
    }

    private void addUsrToDB() {
        try {

            da.createUser("testUser", "123", dni, "testUser", "testUser", false);
            da.modifySaldo(10, dni);

        } catch (UserAlreadyExist e) {
            System.out.println("Usuario ya existía");
        }
    }
    private void addBetToDB(){
        try {

            event = testDA.addEventWithQuestion("a",new Date(),"a", 1);
            forecast = da.createForecast("forecast1", 1.2F, event.getQuestions().get(0).getQuestionNumber());
            bet = da.createBet(dni, 10, forecast.getForecastNumber());

        }catch (Exception e){

        }
    }


    @Test
    public void test1DNINull(){
        try {

            da.modifyBet(10, 1, null);

            fail("No se ha lanzado la excepción esperada");

        }catch (UserDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test2BetNumberNegative(){
        try {

            da.modifyBet(10, -3, "12345678A");

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test3UserNull(){
        try {

            da.modifyBet(10, 1, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (UserDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test4BetNull(){

        addUsrToDB();

        try {

            da.modifyBet(10, -3, "12345678A");

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test5NotEnoughUserMoney(){
        double expectedBetMoney = 10;
        double expectedUsrMoney = 10;

        addUsrToDB();
        addBetToDB();

        try {

            Bet obtainedBet = da.modifyBet(11, bet.getBetNumber(), dni);
            double obtainedMoney = obtainedBet.getBetMoney();
            double obtainedUsrMoney = da.getUser(dni).getSaldo();

            assertEquals(expectedBetMoney, obtainedMoney, 0.1);
            assertEquals(expectedUsrMoney, obtainedUsrMoney, 0.1);

        }catch (Exception e){
            fail("Excepción inesperada");
        }

    }

    @Test
    public void test6NotEnoughBetMoney(){
        double expectedBetMoney = 10;
        double expectedUsrMoney = 10;

        addUsrToDB();
        addBetToDB();

        try {

            Bet obtainedBet = da.modifyBet(-10, bet.getBetNumber(), dni);
            double obtainedMoney = obtainedBet.getBetMoney();
            double obtainedUsrMoney = da.getUser(dni).getSaldo();

            assertEquals(expectedBetMoney, obtainedMoney, 0.1);
            assertEquals(expectedUsrMoney, obtainedUsrMoney, 0.1);

        }catch (Exception e){
            fail("Excepción inesperada");
        }

    }

    @Test
    public void test7ModifiedBet(){
        double expectedBetMoney = 20;
        double expectedUsrMoney = 0;

        addUsrToDB();
        addBetToDB();

        try {

            Bet obtainedBet = da.modifyBet(10, bet.getBetNumber(), dni);
            double obtainedMoney = obtainedBet.getBetMoney();

            double obtainedUsrMoney = da.getUser(dni).getSaldo();

            assertEquals(expectedBetMoney, obtainedMoney, 0.1);
            assertEquals(expectedUsrMoney, obtainedUsrMoney, 0.1);

        }catch (Exception e){
            fail("Excepción inesperada");
        }

    }
}
