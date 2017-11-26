package ActivityManagement.Controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class TimelineDepartPaneController implements Reloadable {

    @FXML
    private JFXDatePicker datePicker;


    @FXML
    void callSelectDate(ActionEvent event) {


    }

    public void reloadPage()
    {

    }
}
