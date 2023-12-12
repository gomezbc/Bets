import businessLogic.BLFacade;
import businessLogic.BLFacadeLocalImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateQuestionBLBMTest {
     DataAccess dataAccess=Mockito.mock(DataAccess.class);
     Event mockedEvent=Mockito.mock(Event.class);
	
	@InjectMocks
	 BLFacade sut=new BLFacadeLocalImplementation(dataAccess);
	
	//sut.createQuestion:  The event has one question with a queryText. 

	
	@Test
	//sut.createQuestion:  The event has NOT a question with a queryText.
	public void test1() {
			try {
				//define paramaters
				String queryText="proba galdera";
				Float betMinimum=new Float(2);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("05/10/2022");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				//configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.doReturn(new Question(queryText, betMinimum,mockedEvent)).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

				

				//invoke System Under Test (sut) 
				Question q=sut.saveQuestion(mockedEvent);
				
				//verify the results
				//Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
				
				
				ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
				ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
				ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);
				
				Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(eventCaptor.capture(),questionStringCaptor.capture(), betMinimunCaptor.capture());
				Float f=betMinimunCaptor.getValue();

				assertEquals(eventCaptor.getValue(),mockedEvent);
				assertEquals(questionStringCaptor.getValue(),queryText);
				assertEquals(betMinimunCaptor.getValue(),betMinimum);

			   } catch (QuestionAlreadyExist e) {
				// TODO Auto-generated catch block
				assertTrue(true);
				} catch (EventFinished e) {
				    fail();
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
	@Test
	//sut.createQuestion:  The event is null.
	public void test3() {
		try {
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.doReturn(null).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

			

			//invoke System Under Test (sut) 
			Question q=sut.saveQuestion(null);
			
			//verify the results
			Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
			
			

			assertNull(q);

		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			fail();
			} catch (EventFinished e) {
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	@Test
	public void test7() {
		String queryText="proba galdera";
		Float betMinimum=new Float(2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate=null;

		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();

			Mockito.when(dataAccess.
					createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class)))
					.thenThrow(QuestionAlreadyExist.class);

		   } catch (QuestionAlreadyExist e) {

		}

		try {
			sut.saveQuestion(mockedEvent);

			fail("Exception was not thrown");
		}catch (QuestionAlreadyExist e){
			// if the program goes to this point OK
			assertTrue(true);
		}catch (EventFinished e) {
			// if the program goes to this point fail
			fail();
			e.printStackTrace();
		}
	}
	
	
	
	
		
}
