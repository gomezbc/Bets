package exceptions;

public class BetDoesntExist extends Exception{
	
	  private static final long serialVersionUID = 1L;
	  public BetDoesntExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the event has already finished
	  *@param s String of the exception
	  */
	  public BetDoesntExist(String s)
	  {
	    super(s);
	  }
}
