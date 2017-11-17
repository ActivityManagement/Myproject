package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CreateDeptController implements Reloadable{

    @FXML
    private JFXTextField deptname_box;
    

    @FXML
    private Label create_status;


    @FXML
    void clickBackButton(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainactpage);
        reloadPage(); //could reload when change scene
    }

    @FXML
    void clickClearButton(ActionEvent event) {
        reloadPage();
    }

    @FXML
    void clickConfirmButton(ActionEvent event) {
        String deptname = deptname_box.getText();

        String deptid = null;
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.DBName);
        if (!odb.isRecordExist("Department")) // check if does't exists any act
            deptid = "000000";
        else
        {
            int cid = 0;
            TypedQuery<Department> query = em.createQuery("SELECT a FROM Department a", Department.class);
            List<Department> results = query.getResultList();
            for (Department a : results) {
                cid = a.getId();
            }
            deptid = String.format("%06d",cid+1);
        }
        Department dept = new Department(deptname,MainProgram.personCurrent.getId(),1);
        //dept.addMember(MainProgram.personCurrent);
        //HasActivity hact = new HasActivity(dept,1);
        //odb.saveObject(dept);
        //odb.saveObject(hact);
        odb.closeConnection();

        // update hasact in person
        em = odb.createConnection(MainProgram.DBName);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+MainProgram.personCurrent.getId()+"", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
           // p.addAct(hact);
            MainProgram.personCurrent = p;
        }
        em.getTransaction().commit();
        odb.closeConnection();
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainpage);
        MainProgram.stageMainPage.reloadPage(); //reload to refresh act
        reloadPage(); //could reload when change scene
    }

    @Override
    public void reloadPage() {
        deptname_box.clear();
        create_status.setText("");
    }
}
