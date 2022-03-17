package rpg;

import java.io.*;
import java.util.List;

public class Loader
{
    private static File databaseFile;
    private static final String HOME_DIRECTORY = System.getProperty("user.home");
    private static final String ACME_HOME = HOME_DIRECTORY + "\\ACME";
    private static String ERROR_MESSAGE = "";

    public static boolean loadDataFile()
    {
//        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(databaseFile)))
//        {
//            List<User> users = (List<User>) inputStream.readObject();
//            User.setAllUsers(users);
//            List<Desk> desks = (List<Desk>) inputStream.readObject();
//            Desk.setAllDesks(desks);
//            return true;
//        }
//        catch (IOException | ClassNotFoundException exception)
//        {
//            ERROR_MESSAGE = exception.getMessage();
//        }
        return false;
    }


    public static String getErrorMessage()
    {
        return ERROR_MESSAGE;
    }

    public static boolean saveDataFile()
    {
        try
        {
            new PrintWriter(databaseFile).close();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(databaseFile));

            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (IOException exception)
        {
            ERROR_MESSAGE = exception.getMessage();
        }
        return false;
    }

}
