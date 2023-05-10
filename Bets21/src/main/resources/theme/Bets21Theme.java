package theme;
import com.formdev.flatlaf.FlatLightLaf;

public class Bets21Theme extends FlatLightLaf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static boolean setup() {
        return setup( new Bets21Theme() );
    }

    @Override
    public String getName() {
        return "Bets21Theme";
    }
}
