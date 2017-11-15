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
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class MainDeptController implements Reloadable {
    @FXML
    void callBacktoAct(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainactpage);
        reloadPage(); //could reload when change scene
        MainProgram.stageMainActPage.reloadPage();
    }

    @FXML
    void callCollapse(ActionEvent event) {

    }

    @Override
    public void reloadPage() {

    }
}
