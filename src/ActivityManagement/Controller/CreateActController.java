package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Activity;
import ActivityManagement.Model.HasActivity;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.Person;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class CreateActController implements Reloadable {
    @FXML
    private JFXTextField actname_box;
    @FXML
    private JFXTextField orgname_box;
    @FXML
    private JFXTextField password_box;
    @FXML
    private JFXTextField descript_box;
    @FXML
    private Label create_status;

    @FXML
    void clickBackButton(ActionEvent event) {
        //TODO
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainpage());
        reloadPage(); //could reload when change scene
    }

    @FXML
    void clickClearButton(ActionEvent event) {
        reloadPage();
    }

    @FXML
    void clickConfirmButton(ActionEvent event) {
        String actname = actname_box.getText();
        String orgname = orgname_box.getText();
        String password = password_box.getText();
        String desc = descript_box.getText();
        boolean check = false;
        check = checkName(actname)&&checkOrg(orgname)&&checkPassword(password);

        if (check)
        {
            //TODO
//            create_status.setText("ถูกแล้วจ้าาา");
            String actid = null;
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            if (!odb.isRecordExist("Activity")) // check if does't exists any act
                actid = "000000";
            else
            {
                int cid = 0;
                TypedQuery<Activity> query = em.createQuery("SELECT a FROM Activity a", Activity.class);
                List<Activity> results = query.getResultList();
                for (Activity a : results) {
                    cid = Integer.parseInt(a.getActid());
                }
                actid = String.format("%06d",cid+1);
            }
            Activity act = new Activity(actid,actname,orgname,password,desc);
            act.addMember(MainProgram.getPersonCurrent());
            HasActivity hact = new HasActivity(act,1);
            odb.saveObject(act);
            odb.saveObject(hact);
            odb.closeConnection();

            // update hasact in person
            em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+MainProgram.getPersonCurrent().getId()+"", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                p.addAct(hact);
                MainProgram.setPersonCurrent(p);
            }
            em.getTransaction().commit();
            odb.closeConnection();

            MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainpage());
            MainProgram.getStageMainPage().reloadPage(); //reload to refresh act
            reloadPage(); //could reload when change scene
        }
    }

    private boolean checkName(String actname)
    {
        if (actname.length()==0)
        {
            create_status.setText("Enter activity name");
            return false;
        }
        return true;
    }

    private boolean checkOrg(String orgname)
    {
        if (orgname.length()==0)
        {
            create_status.setText("Enter organize name");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password)
    {
        boolean value = false;
        if (password.length() < 8)
            create_status.setText("Password must be at least 8 characters");
        else if (password.length() > 16)
            create_status.setText("Password must be less than 16 characters ");
        else if (!truePassword(password))
            create_status.setText("Password must be include A-Z and a-z and 0-9");
        else
            value = true;
        return value;
    }

    private boolean truePassword(String password)
    {
        boolean hascapital = false;
        boolean hasletter = false;
        boolean hasnumber = false;

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                hascapital = true;
            else if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                hasletter = true;
            else if(password.charAt(i) >= '0' && password.charAt(i) <= '9')
                hasnumber = true;
            else
                return false;
        }
        return hascapital&&hasletter&&hasnumber;
    }

    public void reloadPage()
    {
        actname_box.clear();
        orgname_box.clear();
        password_box.clear();
        descript_box.clear();
        create_status.setText("");
    }
}
