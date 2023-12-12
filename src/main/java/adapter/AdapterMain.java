package adapter;

import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import configuration.ConfigXML;
import domain.User;

public class AdapterMain {
    public static void main(String[] args) {
        try {

            // obtener el objeto Facade local
            ConfigXML configXML = ConfigXML.getInstance();
            // establecemos la logica de negocio a local
            configXML.setBusinessLogic(true);
            BLFacade blFacade = BLFacadeFactory.createBLFacadeImplementation(configXML);

            blFacade.getAllUsers().forEach(user -> System.out.println(user.getDni()));
            User user = blFacade.getUserByDni("123456789N");
            AdapterGUI vt = new AdapterGUI(user);
            vt.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}