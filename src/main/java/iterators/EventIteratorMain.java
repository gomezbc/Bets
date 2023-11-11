package iterators;

import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import configuration.ConfigXML;
import domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventIteratorMain {

    public static void main(String[] args) {
        // obtener el objeto Facade local
        ConfigXML configXML = ConfigXML.getInstance();
        // establecemos la logica de negocio a local
        configXML.setBusinessLogic(true);
        BLFacade blFacade = BLFacadeFactory.createBLFacadeImplementation(configXML);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = sdf.parse("17/05/2023"); // 17 del mes que viene
            ExtendedIterator<Event> i = blFacade.getEventsIterator(date);
            Event e;
            System.out.println("_____________________");
            System.out.println("RECORRIDO HACIA ATRÁS");
            i.goLast(); // Hacia atrás
            while (i.hasPrevious()) {
                e = i.previous();
                System.out.println(e.toString());
            }
            System.out.println();
            System.out.println("_____________________");
            System.out.println("RECORRIDO HACIA ADELANTE");
            i.goFirst(); // Hacia adelante
            while (i.hasNext()) {
                e = i.next();
                System.out.println(e.toString());
            }
        } catch (ParseException e1) {
            System.out.println("Problems with date?? " + "17/12/2020");
        }
    }
}
