package exceptions;

public class EventAlreadyExist extends Exception  {
	
	private static final long serialVersionUID = 1L;

	public EventAlreadyExist() {
		super();
	}
	
	public EventAlreadyExist (String s) {
		super(s);
	}

}


