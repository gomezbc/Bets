package exceptions;

public class EventHasntFinished extends Exception{
	
	  private static final long serialVersionUID = 1L;
	  public EventHasntFinished()
	  {
	    super();
	  }
	  /**This exception is triggered if the event has already finished
	  *@param s String of the exception
	  */
	  public EventHasntFinished(String s)
	  {
	    super(s);
	  }
}
