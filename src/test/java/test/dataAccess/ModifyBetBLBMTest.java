package test.dataAccess;

import businessLogic.BLFacadeLocalImplementation;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Forecast;
import domain.User;
import exceptions.BetDoesntExist;
import exceptions.UserDoesntExist;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ModifyBetBLBMTest {

    static DataAccess daMock = Mockito.mock(DataAccess.class);
    static BLFacadeLocalImplementation blf = new BLFacadeLocalImplementation(daMock);

    String dni = "12345678A";
    User usr = Mockito.mock(User.class);
    Forecast forecastMock = Mockito.mock(Forecast.class);

    ArgumentCaptor<Float> argBetMoney = ArgumentCaptor.forClass(Float.class);
    ArgumentCaptor<Integer> argBetNum = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> argDni = ArgumentCaptor.forClass(String.class);


    @Before
    public void resetMockito(){
        Mockito.reset(daMock);
    }

    private void testWhenReturnsBet(double expectedBetMoney, float betMoney){

        try {
            Bet expectedBet = new Bet(usr, (float)expectedBetMoney, forecastMock);
            Mockito.doReturn(expectedBet).when(daMock).modifyBet(betMoney, 1, dni);

            Bet obtainedBet = blf.changeBetMoney(betMoney, 1, dni);

            Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());

            double obtainedMoney = obtainedBet.getBetMoney();

            assertEquals(expectedBetMoney, obtainedMoney, 0.1);

            assertEquals(argBetMoney.getValue(), betMoney, 0.1);
            assertEquals(argBetNum.getValue(), new Integer(1));
            assertEquals(argDni.getValue(), dni);

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test1ModifiedBet(){
        testWhenReturnsBet(10, 10);
    }

    @Test
    public void test2NotEnoughUserMoney(){
        testWhenReturnsBet(10, 11);
    }

    @Test
    public void test3NotEnoughBetMoney(){
        testWhenReturnsBet(10, -10);
    }

    @Test
    public void test4DNINull(){
        try {
            Mockito.doThrow(new UserDoesntExist("El dni es nulo")).when(daMock)
                    .modifyBet(Mockito.any(Float.class), Mockito.any(Integer.class), Mockito.isNull());

            daMock.modifyBet(10, 1, null);

            fail("No se ha lanzado la excepción esperada");

        }catch (UserDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(1), argBetNum.getValue());
            assertNull(argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test5UserNull(){
        try {
            //Suponemos que el usuario "12345678A" no está en la base de datos
            Mockito.doThrow(new UserDoesntExist("El usuario es nulo")).when(daMock)
                    .modifyBet(10, 1, dni);

            daMock.modifyBet(10, 1, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (UserDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(1), argBetNum.getValue());
            assertEquals(dni, argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test6BetNumberNegative(){
        try {

            Mockito.doThrow(new BetDoesntExist("La apuesta debe ser mayor a 0")).when(daMock)
                    .modifyBet(10, -3, dni);

            daMock.modifyBet(10, -3, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(-3), argBetNum.getValue());
            assertEquals(dni, argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void test7BetNull(){
        try {
            //Suponemos que la apuesta 1 no está en la BD
            Mockito.doThrow(new BetDoesntExist("La apuesta introducida no existe")).when(daMock)
                    .modifyBet(10, 1, dni);

            daMock.modifyBet(10, 1, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(1), argBetNum.getValue());
            assertEquals(dni, argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit1(){
        try {

            Mockito.doThrow(new BetDoesntExist("La apuesta debe ser mayor a 0")).when(daMock)
                    .modifyBet(10, -1, dni);

            daMock.modifyBet(10, -1, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(-1), argBetNum.getValue());
            assertEquals(dni, argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit2(){
        try {

            Mockito.doThrow(new BetDoesntExist("La apuesta debe ser mayor a 0")).when(daMock)
                    .modifyBet(10, 0, dni);

            daMock.modifyBet(10, 0, dni);

            fail("No se ha lanzado la excepción esperada");

        }catch (BetDoesntExist e){
            //Excepción esperada

            try {
                Mockito.verify(daMock, Mockito.times(1)).modifyBet(argBetMoney.capture(), argBetNum.capture(), argDni.capture());
            } catch (Exception e2) {
                fail("Excepción inesperada");
            }

            assertEquals(10, argBetMoney.getValue(), 0.1);
            assertEquals(new Integer(0), argBetNum.getValue());
            assertEquals(dni, argDni.getValue());

        }catch (Exception e){
            e.printStackTrace();
            fail("Excepción inesperada");
        }
    }

    @Test
    public void testLimit3(){
        testWhenReturnsBet(20, 10);
    }
    @Test
    public void testLimit4(){
        testWhenReturnsBet(19, 9);
    }
    @Test
    public void testLimit5(){
        testWhenReturnsBet(20, 10);
    }
    @Test
    public void testLimit6(){
        testWhenReturnsBet(10, 11);
    }
    @Test
    public void testLimit7(){
        testWhenReturnsBet(10, -11);
    }
    @Test
    public void testLimit8(){
        testWhenReturnsBet(10, -10);
    }
    @Test
    public void testLimit9() {
        testWhenReturnsBet(1, -9);
    }
}
