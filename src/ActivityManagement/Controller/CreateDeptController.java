package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Activity;
import ActivityManagement.Model.Department;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CreateDeptController implements Reloadable {

    @FXML
    private JFXTextField deptname_box;

    @FXML
    private Label ValidationText;

    @FXML
    private Label create_status;


    @FXML
    void clickBackButton(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainactpage());
        reloadPage(); //could reload when change scene
    }

    @FXML
    void clickClearButton(ActionEvent event) {
        reloadPage();
    }

    @FXML
    void clickConfirmButton(ActionEvent event) {
        String deptname = deptname_box.getText();
        if (deptname.length() != 0 && !deptname.startsWith(" ")) {
            String deptid = null;
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            if (!odb.isRecordExist("Department")) // check if does't exists any act
                deptid = "000000";
            else {
                int cid = 0;
                TypedQuery<Department> query = em.createQuery("SELECT a FROM Department a", Department.class);
                List<Department> results = query.getResultList();
                for (Department a : results) {
                    cid = a.getId();
                }
                deptid = String.format("%06d", cid + 1);
            }
            Department dept = new Department(deptname, MainProgram.getPersonCurrent().getFirstname() +" "+ MainProgram.getPersonCurrent().getLastname());
            //MainProgram.stageMainPage.getCurrentselectact().addDept(dept);
            odb.saveObject(dept);
            odb.closeConnection();

            // update hasact in person
            em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Activity> actquery = em.createQuery("SELECT a FROM Activity a where a.actid = '" + MainProgram.getStageMainPage().getCurrentselectact().getActid() + "'", Activity.class);
            List<Activity> actresults = actquery.getResultList();
            em.getTransaction().begin();
            for (Activity a : actresults) {
                a.addDept(dept);
                MainProgram.getStageMainPage().setCurrentselectact(a);
            }
            em.getTransaction().commit();
            odb.closeConnection();

            MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainactpage());
            MainProgram.getStageMainActPage().reloadPage();
            MainProgram.getStageMainActPage().calltoShowDepartmentPane(null);
            reloadPage(); //could reload when change scene
        }
        else{
            ValidationText.setText("Enter department name");
        }
    }

    @Override
    public void reloadPage() {
        //MainProgram.updateDepartment();
        deptname_box.clear();
        ValidationText.setText("");
    }
}
