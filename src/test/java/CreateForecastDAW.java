import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Forecast;
import domain.Question;
import exceptions.DescriptionDoesntExist;
import exceptions.ForecastAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionDoesntExist;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import test.dataAccess.TestDataAccess;

import javax.persistence.EntityManager;
import java.util.Date;

public class CreateForecastDAW {
    static DataAccess dataAccess = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();
    Event event;
    @Before
    public void setUp() throws QuestionAlreadyExist {
        addEvent();
        dataAccess.open(false);
    }
    @After
        public void tearDown() {
        removeData();
        dataAccess.close();
    }
    public void removeData(){
        testDA.removeEvent(event);
    }
    public void addEvent(){
        try {
            event = testDA.addEventWithQuestion("Event 1",new Date(), "Question 1", 1);
        } catch (Exception e) {

        }
    }
    @Test
    public void testCreateForecastWithNullDescription() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        try{
            dataAccess.createForecast("", 1.2F, 1);
        }catch (DescriptionDoesntExist e) {

        }
    }
    @Test
    public void testCreateForecastQuestionDoesntExist() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        try{
            dataAccess.createForecast("description", 1.2F, 0);
        } catch (QuestionDoesntExist e) {
        }
    }
    @Test
    public void testCreateForecastWithExistingForecast() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        try {
            dataAccess.createForecast("description", 1.2F, 1);
        } catch (ForecastAlreadyExist e) {

        }
    }
    @Test
    public void testCreateForecastSuccess() throws ForecastAlreadyExist, QuestionDoesntExist, DescriptionDoesntExist {
        try {
        dataAccess.createForecast("description", 1.2F, 1);

        } catch (ForecastAlreadyExist | QuestionDoesntExist | DescriptionDoesntExist e) {

        }
    }
}
