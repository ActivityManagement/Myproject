package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPageController implements Reloadable {

    @FXML
    private Label fnameLabel;

    @FXML
    private Label lnameLabel;


    @FXML
    void callCreateAct(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.createact);

    }

    public void setFnameLabel(String name)
    {
        fnameLabel.setText(name);
    }
    public void setLnameLabel(String name)
    {
        lnameLabel.setText(name);
    }

    @Override
    public void reloadPage() {

    }
}
