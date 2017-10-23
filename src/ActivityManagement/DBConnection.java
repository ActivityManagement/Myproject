package ActivityManagement;


import javax.swing.plaf.nimbus.State;
import java.sql.*;

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

    public void createTable(String tform)
    {
        try {
            conn.createStatement().execute(tform);
        } catch (SQLException e) {
            return;
//            e.printStackTrace();
        }
    }

    public void insertToTable(String tform)
    {
        try {
            conn.createStatement().execute(tform);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAll(String table)
    {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet res = statement.executeQuery("Select * FROM "+table+"");
            while (res.next())
            {
                System.out.println(res.getString("UID")+" "+res.getString("FirstName")+" "+res.getString("Password"));
            }
        } catch (SQLException e) {
            return;
//            e.printStackTrace();
        }
    }

    public String getValueinTable(String table,String col,String key,String get)
    {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet res = statement.executeQuery("Select * FROM "+table+" WHERE "+col+"='"+key+"'");
            if (!res.next()){
                //ResultSet is empty
                return null;
            }
            else
                return res.getString(get);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
