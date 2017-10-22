package ActivityManagement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:ActivityManagermentDB;create=true";

    Connection conn;

    public DBConnection()
    {
        try {
            this.conn = DriverManager.getConnection(JDBC_URL);
            if (this.conn != null)
                System.out.println("Connected to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tname)
    {
        try {
            conn.createStatement().execute("Create TABLE "+tname+" (UID varchar(10),FirstName varchar(50),LastName varchar(50),Password varchar(16))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertToTable(String tname,String userid,String fname,String lname,String pass)
    {
        try {
            conn.createStatement().execute("INSERT INTO "+tname+" Values ('"+userid+"','"+fname+"','"+lname+"','"+pass+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
