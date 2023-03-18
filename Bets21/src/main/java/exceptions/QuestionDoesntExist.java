package exceptions;

public class QuestionDoesntExist extends Exception  {
	
	private static final long serialVersionUID = 1L;

	public QuestionDoesntExist() {
		super();
	}
	
	public QuestionDoesntExist (String s) {
		super(s);
	}

}


