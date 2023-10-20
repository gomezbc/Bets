package dataAccess;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.BetDoesntExist;
import exceptions.UserDoesntExist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataAccessModifyBet {
    protected static EntityManager db;
    protected static EntityManagerFactory emf;


    ConfigXML c=ConfigXML.getInstance();

    public DataAccessModifyBet(boolean initializeMode)  {

        System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

        open(initializeMode);

    }

    public DataAccessModifyBet()  {
        this(false);
    }


    /**
     * This is the data access method that initializes the database with some events and questions.
     * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
     */
    public void initializeDB(){

        db.getTransaction().begin();
        try {


            Calendar today = Calendar.getInstance();

            int month=today.get(Calendar.MONTH);
            month+=1;
            int year=today.get(Calendar.YEAR);
            if (month==12) { month=0; year+=1;}

            Event ev1=new Event(1, "Atletico-Athletic", UtilDate.newDate(year,month,17));
            Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
            Event ev3=new Event(3, "Getafe-Celta De Vigo", UtilDate.newDate(year,month,17));
            Event ev4=new Event(4, "Alaves-Deportivo", UtilDate.newDate(year,month,17));
            Event ev5=new Event(5, "Espanyol-Villareal", UtilDate.newDate(year,month,17));
            Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
            Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
            Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
            Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
            Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

            Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
            Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
            Event ev13=new Event(13, "Getafe-Celta De Vigo", UtilDate.newDate(year,month,1));
            Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
            Event ev15=new Event(15, "Espanyol-Villareal", UtilDate.newDate(year,month,1));
            Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));


            Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
            Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
            Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
            Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));

            Question q1;
            Question q2;
            Question q3;
            Question q4;
            Question q5;
            Question q6;

            if (Locale.getDefault().equals(new Locale("es"))) {
                q1=ev1.addQuestion("¿Quién ganará el partido?",1);
                q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
                q3=ev11.addQuestion("¿Quién ganará el partido?",1);
                q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
                q5=ev17.addQuestion("¿Quién ganará el partido?",1);
                q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
            }
            else if (Locale.getDefault().equals(new Locale("en"))) {
                q1=ev1.addQuestion("Who will win the match?",1);
                q2=ev1.addQuestion("Who will score first?",2);
                q3=ev11.addQuestion("Who will win the match?",1);
                q4=ev11.addQuestion("How many goals will be scored in the match?",2);
                q5=ev17.addQuestion("Who will win the match?",1);
                q6=ev17.addQuestion("Will there be goals in the first half?",2);
            }
            else {
                q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
                q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
                q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
                q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
                q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
                q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);

            }

            q1.addForecast("Athletic", 1.5f ,q1);
            q1.addForecast("Atlético", 1.4f ,q1);
            q2.addForecast("Athletic", 1.8f ,q2);
            q2.addForecast("Atlético", 1.2f ,q2);

            db.persist(q1);
            db.persist(q2);
            db.persist(q3);
            db.persist(q4);
            db.persist(q5);
            db.persist(q6);


            db.persist(ev1);
            db.persist(ev2);
            db.persist(ev3);
            db.persist(ev4);
            db.persist(ev5);
            db.persist(ev6);
            db.persist(ev7);
            db.persist(ev8);
            db.persist(ev9);
            db.persist(ev10);
            db.persist(ev11);
            db.persist(ev12);
            db.persist(ev13);
            db.persist(ev14);
            db.persist(ev15);
            db.persist(ev16);
            db.persist(ev17);
            db.persist(ev18);
            db.persist(ev19);
            db.persist(ev20);


            db.getTransaction().commit();
            System.out.println("Db initialized");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * This method modifies the bet Money of a bet
     * @param betMoney the amount of money that the user bets
     * @param betNumber number of the bet to modify
     * @param dni dni of the user
     * @return BetDoesntExist if the bet does not exist in the database, current Bet if the bet is not possible
     * @throws UserDoesntExist if the user does not exist in the database**/

    public Bet modifyBet (float betMoney, int betNumber, String dni) throws BetDoesntExist, UserDoesntExist {

        if(dni == null){
            System.err.println(">> DataAccess: modifyBet => error UserDoesntExist: error, dni nulo");
            throw new UserDoesntExist("El usuario introducido no es correcto");
        }
        if(betNumber < 0){
            System.err.println(">> DataAccess: modifyBet => error BetDoesntExist: error, identificador negativo");
            throw new BetDoesntExist("No existe la apuesta a modificar");
        }

        Bet bet = db.find(Bet.class, betNumber);
        User user = db.find(User.class, dni);

        if (user == null) {
            System.err.println(">> DataAccess: modifyBet => error UserDoesntExist: No existe un usuario con este DNI en la base de datos, dni="+dni);
            throw new UserDoesntExist("No existe un usuario con este DNI en la base de datos, dni="+dni);
        }
        if (bet == null) {
            System.err.println(">> DataAccess: modifyBet => error BetDoesntExist: No existe la apuesta ha modificar");
            throw new BetDoesntExist("No existe la apuesta a modificar");
        }

        double betMoneyBefore = bet.getBetMoney();
        double betMoneyAfter = betMoney + betMoneyBefore;
        float userMoney = user.getSaldo();

        if(userMoney >= betMoney && betMoneyAfter > 0){

            user.setSaldo(userMoney - betMoney);
            bet.setBetMoney((float)betMoneyAfter);

            db.getTransaction().begin();
            db.persist(user);
            db.persist(bet);
            db.getTransaction().commit();
        }
        return bet;
    }



    public void open(boolean initializeMode){

        System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

        String fileName=c.getDbFilename();
        if (initializeMode) {
            fileName=fileName+";drop";
            System.out.println("Deleting the DataBase");
        }

        if (c.isDatabaseLocal()) {
            emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
            db = emf.createEntityManager();
        } else {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.user", c.getUser());
            properties.put("javax.persistence.jdbc.password", c.getPassword());

            emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

            db = emf.createEntityManager();
        }

    }

    public void close(){
        db.close();
        System.out.println("DataBase closed");
    }

    public void emptyDatabase() {

        try{

            Path pathArchivo1 = Paths.get(c.getDbFilename());
            Path pathArchivo2 = Paths.get(c.getDbFilename()+"$");

            Files.delete(pathArchivo1);
            Files.delete(pathArchivo2);

        }catch (IOException e){
            System.err.println("Error al eliminar archivos");
        }

    }

    public void removeUser(String dni) {
    }

    public void removeBet(Integer betNumber) {
    }

    public void createUser(String testUser, String number, String dni, String testUser1, String testUser2, boolean b) {
    }

    public void modifySaldo(int i, String dni) {
    }

    public Forecast createForecast(String forecast1, float v, Integer questionNumber) {
        return null;
    }

    public Bet createBet(String dni, int i, Integer forecastNumber) {
        return null;
    }

    public User getUser(String dni) {
        return null;
    }
}
