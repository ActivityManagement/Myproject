package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController implements Reloadable {
    @FXML
    private JFXTextField userid_box;
    @FXML
    private JFXPasswordField pass_box;
    @FXML
    private Label status_login;

    @FXML
    void callLoginEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();
        if (checkLogin(userid,pass))
        {
            // go to main scene
            //TODO
            status_login.setText("");
            MainProgram.UIDCurrent = userid;
            MainProgram.primaryWindow.getScene().setRoot(MainProgram.createact);
            reloadPage(); //could reload when change scene
        }
    }
    @FXML
    void callRegisterEvent(ActionEvent event) {
//        String userid = userid_box.getText();
//        String pass = pass_box.getText();
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
        String getpass = null;
        if (getpass!=null && getpass.equals(pass)) return true;
        status_login.setText("Username or Password invalid");
        return false;
    }

    public void reloadPage()
    {
        userid_box.clear();
        pass_box.clear();
        status_login.setText("");
    }
}
