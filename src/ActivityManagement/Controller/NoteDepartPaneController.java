package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Department;
import ActivityManagement.Model.ObjectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NoteDepartPaneController implements Reloadable {

    @FXML
    private TextArea noteFieldArea;

    @Override
    public void reloadPage()
    {
        MainProgram.updateDepartment();
        noteFieldArea.setText(MainProgram.getStageDeptPane().getCurrentselectdept().getNote());
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
