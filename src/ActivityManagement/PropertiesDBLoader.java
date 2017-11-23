package ActivityManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesDBLoader {

    private static String database;
    private static String user;
    private static String pass;

    public static void load()
    {
        Properties prop = new Properties();
        InputStream input = null;
        input = PropertiesDBLoader.class.getResourceAsStream("config.properties");
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = prop.getProperty("database");
        user = prop.getProperty("dbuser");
        pass = prop.getProperty("dbpass");
    }

    public static String getDatabase()
    {
        return database;
    }
    public static String getUser()
    {
        return user;
    }
    public static String getPass()
    {
        return pass;
    }

}
