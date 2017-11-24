package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ActPageController implements Reloadable{

    @FXML
    private AnchorPane mainactpane;
    @FXML
    private Label titleact;

    @FXML
    void callBacktoHome(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainpage());
    }

    public AnchorPane getMainactpane() {
        return mainactpane;
    }

    @Override
    public void reloadPage() {
        mainactpane.getChildren().clear();
        titleact.setText(MainProgram.getStageMainPage().getCurrentselectact().getActname());
    }

    @FXML
    void calltoShowMemberPane(ActionEvent event) {
        //TODO
        // remain anchor with children when add new node
        mainactpane.getChildren().clear();
        mainactpane.getChildren().add(MainProgram.getMemberactpane());
        fitNodetoParent(MainProgram.getMemberactpane());
        MainProgram.getStageMemberActPane().reloadPage();
    }

    @FXML
    void calltoShowDepartmentPane(ActionEvent event) {
        mainactpane.getChildren().clear();
        mainactpane.getChildren().add(MainProgram.getDeptPane());
        fitNodetoParent(MainProgram.getDeptPane());
        MainProgram.getStageDeptPane().reloadPage();
    }

    private void fitNodetoParent(Node nd)
    {
        AnchorPane.setTopAnchor(nd,0.0);
        AnchorPane.setLeftAnchor(nd,0.0);
        AnchorPane.setRightAnchor(nd,0.0);
        AnchorPane.setBottomAnchor(nd,0.0);
    }
}
