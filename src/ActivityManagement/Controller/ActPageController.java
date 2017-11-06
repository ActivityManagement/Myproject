package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ActPageController implements Reloadable{

    @FXML
    private AnchorPane mainactpane;

    @FXML
    void callBacktoHome(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainpage);
    }

    public AnchorPane getMainactpane() {
        return mainactpane;
    }

    @Override
    public void reloadPage() {
        mainactpane.getChildren().clear();
    }

    @FXML
    void calltoShowMemberPane(ActionEvent event) {
        mainactpane.getChildren().clear();
        mainactpane.getChildren().add(MainProgram.memberactpane);
    }
}
