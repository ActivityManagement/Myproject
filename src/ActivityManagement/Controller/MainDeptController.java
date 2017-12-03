package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class MainDeptController implements Reloadable {

    @FXML
    private Label titleactdept;

    @FXML
    private AnchorPane departmentPane;

    @FXML
    void callBacktoAct(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainactpage());
        reloadPage(); //could reload when change scene
        MainProgram.getStageMainActPage().reloadPage();
    }


    @FXML
    void callCollapse(ActionEvent event) {

    }

    @FXML
    void clickNoteButton(ActionEvent event) {
        departmentPane.getChildren().clear();
        departmentPane.getChildren().add(MainProgram.getNoteDepartPane());
        fitNodetoParent(MainProgram.getNoteDepartPane());
        MainProgram.getStageNoteDepartPane().reloadPage();

    }

    @FXML
    void clickTimelineButton(ActionEvent event) {
        departmentPane.getChildren().clear();
        departmentPane.getChildren().add(MainProgram.getTimelineDepartPane());
        fitNodetoParent(MainProgram.getTimelineDepartPane());
        MainProgram.getStageTimelineDepartPane().reloadPage();
    }

    @FXML
    void clickPollButton(ActionEvent event) {

    }

    private void fitNodetoParent(Node nd)
    {
        AnchorPane.setTopAnchor(nd,0.0);
        AnchorPane.setLeftAnchor(nd,0.0);
        AnchorPane.setRightAnchor(nd,0.0);
        AnchorPane.setBottomAnchor(nd,0.0);
    }

    @Override
    public void reloadPage() {
        departmentPane.getChildren().clear();
        String title = String.format("%s : %s",MainProgram.getStageMainPage().getCurrentselectact().getActname(),MainProgram.getStageDeptPane().getCurrentselectdept().getDeptName());
        titleactdept.setText(title);
    }
}
