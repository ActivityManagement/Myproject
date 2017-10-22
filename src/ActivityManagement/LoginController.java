package ActivityManagement;

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

    private String aid = "eakarin01";
    private String apass= "123456789";

    @FXML
    void callLoginEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();
        if (checkLogin(userid,pass))
        {
            // go to main scene
            //TODO
            MainProgram.primaryWindow.setTitle("New Manage");

        }
    }
    @FXML
    void callRegisterEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();

        if (!userid.isEmpty() && !pass.isEmpty())
        {
            DBConnection bConnection = new DBConnection();
            bConnection.createTable("Login");
            bConnection.insertToTable("Login",userid,"admin","kub",pass);
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
        if (userid.equals(this.aid) && pass.equals(this.apass)) return true;
        status_login.setText("Username or Password invalid");
        return false;
    }

}
