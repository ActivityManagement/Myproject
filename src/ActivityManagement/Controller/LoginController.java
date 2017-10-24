package ActivityManagement.Controller;

import ActivityManagement.Model.DBConnection;
import ActivityManagement.MainProgram;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private JFXTextField userid_box;
    @FXML
    private JFXPasswordField pass_box;
    @FXML
    private Label status_login;

    private final String tableName = "Login";
    private final String createTBF = "Create TABLE "+tableName+" (UID varchar(10),FirstName varchar(50),LastName varchar(50),Password varchar(16))";

    @FXML
    void callLoginEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();
        if (checkLogin(userid,pass))
        {
            // go to main scene
            //TODO
            status_login.setText("");
            MainProgram.primaryWindow.setScene(MainProgram.createactScene);

        }
    }
    @FXML
    void callRegisterEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();

        if (!userid.isEmpty() && !pass.isEmpty())
        {
            DBConnection bConnection = new DBConnection();
            bConnection.createTable(createTBF);
//            if (bConnection.getValueinTable(tableName,"UID",userid,"Password")==null)
//                bConnection.insertToTable("INSERT INTO "+tableName+" Values ('"+userid+"','Admin','kub','"+pass+"')");
//            else
////                repeat UID
//                System.out.println("Repeat");
        }

    }

    private boolean checkLogin(String userid,String pass)
    {
        boolean value = false;
        if (userid.length()==0 || pass.length()==0)
            status_login.setText("Please enter Username and Password");
        else
            value = matchLoginDB(userid,pass);
        return value;
    }

    private boolean matchLoginDB(String userid,String pass)
    {
        //TODO
        DBConnection bConnection = new DBConnection();
//        bConnection.printAll("Login");
        String getpass = bConnection.getValueinTable(tableName,"UID",userid,"Password");
        if (getpass!=null && getpass.equals(pass)) return true;
        status_login.setText("Username or Password invalid");
        return false;
    }

}
