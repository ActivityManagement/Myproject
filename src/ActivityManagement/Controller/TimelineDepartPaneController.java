package ActivityManagement.Controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TimelineDepartPaneController implements Reloadable {

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    void callSelectDate(ActionEvent event) {
        System.out.println("DatePicker");

    }

    public void reloadPage()
    {

    }
}
