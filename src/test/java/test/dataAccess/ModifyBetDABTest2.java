package test.dataAccess;

import dataAccess.DataAccess;
import dataAccess.DataAccessModifyBet;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import exceptions.BetDoesntExist;
import exceptions.UserAlreadyExist;
import exceptions.UserDoesntExist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ModifyBetDABTest2 {
    static DataAccessModifyBet da = new DataAccessModifyBet();
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
        da.removeUser(dni);
    }
    private void removeBetAndEvent() {
        try{
            if(bet != null){
                da.removeBet(bet.getBetNumber());
                testDA.removeEvent(event);
            }
        }catch (Exception e){
            System.out.println("No existía la apuesta");
        }
    }

    private void addUsrToDB() {
        try {

            da.createUser("testUser", "123", dni, "testUser", "testUser", false);
            da.modifySaldo(10, dni);

        } catch (Exception e) {
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

    private void testWhenReturnsBet(double expectedBetMoney, double expectedUsrMoney, int betMoney){
        addUsrToDB();
        addBetToDB();

        try {

            Bet obtainedBet = da.modifyBet(betMoney, bet.getBetNumber(), dni);
            double obtainedMoney = obtainedBet.getBetMoney();

            double obtainedUsrMoney = da.getUser(dni).getSaldo();

            assertEquals(expectedBetMoney, obtainedMoney, 0.1);
            assertEquals(expectedUsrMoney, obtainedUsrMoney, 0.1);

        }catch (Exception e){
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test1ModifiedBet(){
       testWhenReturnsBet(20, 0, 10);
    }

    @Test
    public void test2NotEnoughUserMoney(){
        testWhenReturnsBet(10, 10, 11);
    }

    @Test
    public void test3NotEnoughBetMoney(){
        testWhenReturnsBet(10, 10, -10);
    }

    @Test
    public void test4DNINull(){
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
    public void test5UserNull(){
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
    public void test6BetNumberNegative(){
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
    public void test7BetNull(){

        addUsrToDB();

        try {

            da.modifyBet(10, 1, "12345678A");

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit1(){

        addUsrToDB();

        try {

            da.modifyBet(10, -1, "12345678A");

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit2(){

        addUsrToDB();

        try {

            da.modifyBet(10, 0, "12345678A");

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada
        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit3(){
        testWhenReturnsBet(20, 0, 10);
    }
    @Test
    public void testLimit4(){
        testWhenReturnsBet(19, 1, 9);
    }
    @Test
    public void testLimit5(){
        testWhenReturnsBet(20, 0, 10);
    }
    @Test
    public void testLimit6(){
        testWhenReturnsBet(10, 10, 11);
    }
    @Test
    public void testLimit7(){
        testWhenReturnsBet(10, 10, -11);
    }
    @Test
    public void testLimit8(){
        testWhenReturnsBet(10, 10, -10);
    }
    @Test
    public void testLimit9() {
        testWhenReturnsBet(1, 19, -9);
    }

}
