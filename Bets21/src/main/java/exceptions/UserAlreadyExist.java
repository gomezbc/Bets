package exceptions;

public class UserAlreadyExist extends Exception{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExist() {
		super();
	}
	public UserAlreadyExist(String s) {
		super(s);
	}
}
