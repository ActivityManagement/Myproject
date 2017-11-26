package ActivityManagement.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

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

    public void checkAddButton()
    {
        if (datePicker.getValue()!=null && timePicker.getValue()!=null && !detailInput.getText().trim().isEmpty()) {
            add_button.setDisable(false);
        }
        else {
            add_button.setDisable(true);
        }
    }

    @FXML
    void inputToText(KeyEvent event) {
        checkAddButton();
    }
}
