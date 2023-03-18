package exceptions;

public class ForecastAlreadyExist extends Exception{
	
	  private static final long serialVersionUID = 1L;
	  public ForecastAlreadyExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the event has already finished
	  *@param s String of the exception
	  */
	  public ForecastAlreadyExist(String s)
	  {
	    super(s);
	  }
}
