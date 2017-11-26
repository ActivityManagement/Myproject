package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.Timeline;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class TimelineDepartPaneController implements Reloadable {

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXButton add_button;

    @FXML
    private JFXTextField detailInput;


    @FXML
    void callSelectDate(ActionEvent event) {
        checkAddButton();

    }

    @FXML
    void callSelectTime(ActionEvent event) {
        checkAddButton();

    }

    public void reloadPage()
    {
        timePicker.setIs24HourView(true);
    }

    private void checkAddButton()
    {
        if (datePicker.getValue()!=null && timePicker.getValue()!=null && !detailInput.getText().trim().isEmpty()) {
            add_button.setDisable(false);
        }
        else {
            add_button.setDisable(true);
        }
    }

    private Timeline getTimelineDate(LocalDate date)
    {
        Timeline currentTl = null;
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Timeline> query = em.createQuery("SELECT tl FROM Timeline tl WHERE tl.date = "+date+"", Timeline.class);
        List<Timeline> result = query.getResultList();
        for (Timeline tl: result) {
            currentTl = tl;
        }
        odb.closeConnection();
        if (currentTl==null) //don't have timeline in Database
        {
            Timeline newtl = new Timeline(date);
            odb = new ObjectDB();
            odb.createConnection(MainProgram.getDBName());
            odb.saveObject(newtl);
            odb.closeConnection();
            currentTl = newtl;
        }
        return currentTl;
    }

    @FXML
    void clickAddTimeButton(ActionEvent event) {
        Timeline tl = getTimelineDate(datePicker.getValue());

    }

    @FXML
    void inputToText(KeyEvent event) {
        checkAddButton();
    }
}
