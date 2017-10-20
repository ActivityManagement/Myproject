package ActivityManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField userid_box;
    @FXML
    private PasswordField pass_box;
    @FXML
    private Label status_login;

    @FXML
    void callLoginEvent(ActionEvent event) {
        String userid = userid_box.getText();
        String pass = pass_box.getText();
        if (checkLogin(userid,pass))
        {
            // go to main scene
        }
    }

    private boolean checkLogin(String userid,String pass)
    {
        boolean value = false;
        if (userid.length()==0 || pass.length()==0)
            status_login.setText("กรุณากรอก USER ID และ PASSWORD");
        else
            value = matchLoginDB(userid,pass);
        return value;
    }

    private boolean matchLoginDB(String userid,String pass)
    {
        status_login.setText("USER ID หรือ PASSWORD ไม่ถูกต้อง");
        return false;
    }

}
