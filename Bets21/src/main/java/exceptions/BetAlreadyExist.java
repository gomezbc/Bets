package exceptions;

public class BetAlreadyExist extends Exception{
	private static final long serialVersionUID = 1L;
	
	public BetAlreadyExist() {
		super();
	}
	public BetAlreadyExist(String s) {
		super(s);
	}
}
