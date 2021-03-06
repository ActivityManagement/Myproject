package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.Person;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.persistence.*;
import java.util.List;

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
            status_login.setText("");
            MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainpage());
            reloadPage(); //could reload when change scene
            MainProgram.getStageMainPage().reloadPage(); //reload to refresh act
        }
    }
    @FXML
    void callRegisterEvent(ActionEvent event) {
//        String userid = userid_box.getText();
//        String pass = pass_box.getText();
        ObjectDB odb = new ObjectDB();
        odb.createConnection(MainProgram.getDBName());
        odb.saveObject(new Person());
        odb.closeConnection();
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
        Person pobj = null;
        String getpass = null;
        // get object from database
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> results = query.getResultList();
        for (Person p : results) {
            if (p.getUserid().equals(userid))
            {
                pobj = p;
                getpass = p.getPassword();
            }
        }
        odb.closeConnection();
        if (getpass!=null && getpass.equals(pass))
        {
            MainProgram.setPersonCurrent(pobj);
            return true;
        }
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
