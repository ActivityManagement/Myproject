package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Department;
import ActivityManagement.Model.HasActivity;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class NoteDepartPaneController implements Reloadable {

    @FXML
    private TextArea noteFieldArea;

    @FXML
    private JFXButton AcceptButton;


    @Override
    public void reloadPage()
    {
        MainProgram.updateDepartment();
        noteFieldArea.setText(MainProgram.getStageDeptPane().getCurrentselectdept().getNote());

        ArrayList<HasActivity> hact = MainProgram.getPersonCurrent().getMyact();
        int checkrole = 0;
        for (HasActivity ha : hact) {
            //search has act of this activity
            if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                checkrole = ha.getRole();
                break;
            }
        }
        System.out.println(checkrole);

        if (checkrole == 3) {
            AcceptButton.setDisable(false);
            noteFieldArea.setDisable(false);

        }
        else {
            AcceptButton.setDisable(true);
            noteFieldArea.setDisable(true);
        }
    }

    @FXML
    void callAcceptNote(ActionEvent event) {
        String noteText = noteFieldArea.getText();
        int depID = MainProgram.getStageDeptPane().getCurrentselectdept().getId();
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d WHERE d.id="+depID+"", Department.class);
        List<Department> results = query.getResultList();
        em.getTransaction().begin();
        for (Department d: results) {
            d.setNote(noteText);
            MainProgram.getStageDeptPane().setCurrenselectdept(d);
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }

}
