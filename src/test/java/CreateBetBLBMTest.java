import businessLogic.BLFacade;
import businessLogic.BLFacadeLocalImplementation;
import dataAccess.DataAccess;
import domain.*;
import exceptions.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CreateBetBLBMTest {
    static DataAccess dataAccess = Mockito.mock(DataAccess.class);
    @InjectMocks
    static BLFacade sut = new BLFacadeLocalImplementation(dataAccess);

    Forecast mockedForecast = Mockito.mock(Forecast.class);
    User mockedUser = Mockito.mock(User.class);

    @Test
    // sut.createBet: The bet is instantiated correctly
    public void test1() {
        // define parameters
        String dni = "12345678N";
        Float betMoney = 2.4F;
        Integer forecastNumber = 1;

        // configure Mock
        Mockito.doReturn(forecastNumber).when(mockedForecast).getForecastNumber();
        Mockito.doReturn(dni).when(mockedUser).getDni();
        try {
            Mockito.doReturn(new Bet(mockedUser, betMoney, mockedForecast)).when(dataAccess)
                    .createBet(Mockito.any(String.class), Mockito.any(Float.class), Mockito.any(Integer.class));
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }
        try {
            sut.createBet(mockedUser.getDni(), betMoney, mockedForecast.getForecastNumber());
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }

        ArgumentCaptor<String> dniCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Float> betMoneyCaptor = ArgumentCaptor.forClass(Float.class);
        ArgumentCaptor<Integer> forecastNumberCaptor = ArgumentCaptor.forClass(Integer.class);

        try {
            Mockito.verify(dataAccess, Mockito.atLeast(1)).createBet(dniCaptor.capture(), betMoneyCaptor.capture(),
                    forecastNumberCaptor.capture());
        } catch (UserDoesntExist | ForecastDoesntExist | BetAlreadyExist e) {
            throw new RuntimeException(e);
        }

        assertEquals(dniCaptor.getValue(), dni);
        assertEquals(betMoneyCaptor.getValue(), betMoney);
        assertEquals(forecastNumberCaptor.getValue(), forecastNumber);
    }

    @Test
    // sut.createBet: The bet is already in the database
    public void test2() {
        // define parameters
        String dni = "12345678N";
        float betMoney = 1.4F;
        Integer forecastNumber = 1;

        // configure Mock
        Mockito.doReturn(forecastNumber).when(mockedForecast).getForecastNumber();
        Mockito.doReturn(dni).when(mockedUser).getDni();
        Mockito.doReturn(new Bet(mockedUser, betMoney, mockedForecast)).when(mockedUser).DoesBetExists(forecastNumber);

        try {
            sut.createBet(mockedUser.getDni(), betMoney, mockedForecast.getForecastNumber());
        } catch (BetAlreadyExist e) {
            assertTrue(true);
        } catch (UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // sut.createBet: User is not in the DB
    public void test5() {
        // define parameters
        String dni = "25365678N";
        float betMoney = 2.7F;
        int forecastNumber = 1;

        // configure Mock
        Mockito.doReturn(dni).when(mockedUser).getDni();
        try {
            Mockito.doThrow(new UserDoesntExist()).when(dataAccess).createBet(Mockito.any(String.class),
                    Mockito.any(Float.class), Mockito.any(Integer.class));
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }

        try {
            sut.createBet(mockedUser.getDni(), betMoney, forecastNumber);
            // it should raise an exception
            fail();
        } catch (UserDoesntExist e) {
            assertTrue(true);
        } catch (BetAlreadyExist | ForecastDoesntExist e) {
            // these exception should not be raised
            fail();
        }
    }

    @Test
    // sut.createBet: The betMoney is less than the minimum of the question
    public void test6() {
        // define parameters
        String dni = "12345678N";
        float betMoney = 0.4F;
        int forecastNumber;
        forecastNumber = 1;

        // configure Mock
        Mockito.doReturn(forecastNumber).when(mockedForecast).getForecastNumber();
        Mockito.doReturn(dni).when(mockedUser).getDni();
        try {
            Mockito.doThrow(new UserDoesntExist()).when(dataAccess).createBet(Mockito.any(String.class),
                    Mockito.any(Float.class), Mockito.any(Integer.class));
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }
        try {
            Mockito.doThrow(new Error()).when(dataAccess).createBet(Mockito.any(String.class), Mockito.any(Float.class),
                    Mockito.any(Integer.class));
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }
        try {
            sut.createBet(mockedUser.getDni(), betMoney, mockedForecast.getForecastNumber());
        } catch (Error e) {
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        // if the program continues fail (it should raise an exception because the
        // betMoney is less than the minimum of the question)
        fail("The program doesn't check if the betMoney is less than the minimum of the question");
    }

    @Test
    // sut.createBet: The forecast is not in the database
    public void test7() {
        // define parameters
        String dni = "12345678N";
        float betMoney = 2.4F;
        int forecastNumber = 7;

        // configure Mock
        Mockito.doReturn(forecastNumber).when(mockedForecast).getForecastNumber();
        Mockito.doReturn(dni).when(mockedUser).getDni();
        try {
            Mockito.doThrow(new ForecastDoesntExist()).when(dataAccess).createBet(Mockito.any(String.class),
                    Mockito.any(Float.class), Mockito.any(Integer.class));
        } catch (BetAlreadyExist | UserDoesntExist | ForecastDoesntExist e) {
            throw new RuntimeException(e);
        }

        try {
            sut.createBet(mockedUser.getDni(), betMoney, forecastNumber);
            // it should raise an exception
            fail();
        } catch (ForecastDoesntExist e) {
            assertTrue(true);
        } catch (BetAlreadyExist | UserDoesntExist e) {
            // these exception should not be raised
            fail();
        }
    }

}
