package businessLogic;

import configuration.ConfigXML;
import dataAccess.DataAccess;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class BLFacadeFactory {

    public static BLFacade createBLFacadeImplementation(ConfigXML configXML) {
        if (configXML.isBusinessLogicLocal()) {
            return createNewLocalBLFacade(configXML.getDataBaseOpenMode());
        } else {
            return createNewRemoteBLFacade(configXML.getBusinessLogicNode(), configXML.getBusinessLogicPort(), configXML.getBusinessLogicName());
        }
    }

    private static BlFacadeRemoteImplementation createNewRemoteBLFacade(String businessLogicNode, String businessLogicPort, String businessLogicName){

        //example: URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
        String serviceName= "http://"+businessLogicNode +":"+ businessLogicPort+"/ws/"+businessLogicName+"?wsdl";

        URL url = null;
        try {
            url = new URL(serviceName);
        } catch (MalformedURLException e) {
            System.out.println("Error in ApplicationLauncher: "+e.toString());
            throw new RuntimeException(e);
        }


        //1st argument refers to wsdl document above and 2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

        Service service = Service.create(url, qname);

        return new BlFacadeRemoteImplementation(service);
    }


    //In this option, you can parameterize the DataAccess (e.g. a Mock DataAccess object)
    private static BLFacadeLocalImplementation createNewLocalBLFacade(String dataBaseOpenMode) {

        DataAccess da = new DataAccess(dataBaseOpenMode.equals("initialize"));
        return new BLFacadeLocalImplementation(da);
    }
}
