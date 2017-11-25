package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.Person;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.persistence.*;
import java.util.List;

public class LoginController implements Reloadable {
    @FXML
    private JFXTextField userid_box;
    @FXML
    private JFXPasswordField pass_box;
    @FXML
    private Label status_login;

//    --------------------Register Field--------------------
    @FXML
    private StackPane registerPane;
    @FXML
    private JFXDialogLayout contentofRegister;

    private JFXDialog registerdialog = null;

    @FXML
    private TextField regUserField;

    @FXML
    private TextField regFNameField;

    @FXML
    private TextField regLNameField;

    @FXML
    private TextField regPassField;
//    --------------------------------------------------------

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

        registerPane.setVisible(true);
        if (registerdialog == null) {
            registerdialog = new JFXDialog(registerPane, contentofRegister, JFXDialog.DialogTransition.CENTER);
        }
        registerdialog.show();
    }

    private void clearRegField()
    {
        regUserField.clear();
        regFNameField.clear();
        regLNameField.clear();
        regPassField.clear();
    }

    @FXML
    void callWantToRegister(ActionEvent event) {
        String user = regUserField.getText();
        String fname = regFNameField.getText();
        String lname = regLNameField.getText();
        String pass = regPassField.getText();
        if (!user.equals("") && !pass.equals(""))
        {
            Person p = new Person(user,pass,fname,lname);
            ObjectDB odb = new ObjectDB();
            odb.createConnection(MainProgram.getDBName());
            odb.saveObject(p);
            odb.closeConnection();
            registerdialog.close();
            registerPane.setVisible(false);
            clearRegField();
        }
        else
        {
            System.out.println("error");
        }
    }
    @FXML
    void closeRegisterDialog(MouseEvent event) {
        registerPane.setVisible(false);
        if (registerdialog != null)
            registerdialog.close();
        clearRegField();
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
