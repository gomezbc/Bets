package exceptions;

public class ForecastDoesntExist extends Exception{
	
	  private static final long serialVersionUID = 1L;
	  public ForecastDoesntExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the event has already finished
	  *@param s String of the exception
	  */
	  public ForecastDoesntExist(String s)
	  {
	    super(s);
	  }
}
