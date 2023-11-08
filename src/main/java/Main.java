import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.User;
import gui.AdapterGUI;

public class Main {
    public static void main(String[] args) {
        try {
            BLFacadeImplementation blFacade = new BLFacadeImplementation();
            blFacade.getAllUsers().forEach(user -> System.out.println(user.getDni()));
            User user = blFacade.getUser("123456789N");
            AdapterGUI vt = new AdapterGUI(user);
            vt.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}