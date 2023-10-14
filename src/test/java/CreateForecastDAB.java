import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Forecast;
import domain.Question;
import exceptions.DescriptionDoesntExist;
import exceptions.ForecastAlreadyExist;
import exceptions.QuestionDoesntExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

public class CreateForecastDAB {
    static DataAccess dataAccessMock = Mockito.mock(DataAccess.class);
    @Test
    public void testCreateForecastWithNullDescription() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        Mockito.when(dataAccessMock.createForecast(null, 1.2F, 1)).thenThrow(new DescriptionDoesntExist());
        try {
            dataAccessMock.createForecast(null, 1.2F, 1);
            fail("Se esperaba excepcion DescriptionDoesntExist");
        } catch (DescriptionDoesntExist e) {

        }
    }
    @Test
    public void testCreateForecastQuestionNumberNull() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        /*Mockito.when(dataAccessMock.createForecast("description", 1.2F, null).thenThrow(new QuestionDoesntExist()));
        try {
            dataAccessMock.createForecast("description", 1.2F, null);
            fail("Se esperaba excepcion");
        } catch (Exception e) {

        }*/
    }
   @Test
    public void testCreateForecastQuestionNumberNotInt() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        /*
        Mockito.when(dataAccessMock.createForecast("description", 1.2F, a).thenThrow(new QuestionDoesntExist()));
        try {
            dataAccessMock.createForecast("description", 1.2F, a);
            fail("Se esperaba excepcion");
        } catch (Exception e) {

        }
        */
    }
    @Test
    public void testCreateForecastQuestionDoesntExist() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        Mockito.when(dataAccessMock.createForecast("description", 1.2F, 0)).thenThrow(new QuestionDoesntExist());
        try {
            dataAccessMock.createForecast("description", 1.2F, 0);
            fail("Se esperaba excepcion QuestionDoesntExist");
        } catch (QuestionDoesntExist e) {

        }
    }
    @Test
    public void testCreateForecastGainNotFloat() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        Mockito.when(dataAccessMock.createForecast("description", -5, 0)).thenThrow(new Exception());
        try {
            dataAccessMock.createForecast("description", -5, 0);
        } catch (Exception e) {

        }
    }
    @Test
    public void testCreateForecastWithExistingForecast() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        Mockito.when(dataAccessMock.createForecast("description", 1.2F, 1)).thenThrow(new ForecastAlreadyExist());
        try {
            dataAccessMock.createForecast("description", 1.2F, 1);
            fail("Se esperaba excepcion ForecastAlreadyExist");
        } catch (ForecastAlreadyExist e) {
        }

    }
}
