package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainPageController {

    @FXML
    void callCreateAct(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.createact);

    }
}
