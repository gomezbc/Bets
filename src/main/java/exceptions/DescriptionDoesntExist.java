package exceptions;

public class DescriptionDoesntExist extends Exception  {

    private static final long serialVersionUID = 1L;

    public DescriptionDoesntExist() {
        super();
    }

    public DescriptionDoesntExist (String s) {
        super(s);
    }

}
