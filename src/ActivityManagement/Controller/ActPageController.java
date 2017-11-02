package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ActPageController {

    @FXML
    void callBacktoHome(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainpage);
    }
}
