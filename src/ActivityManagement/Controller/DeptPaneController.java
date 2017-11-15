package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DeptPaneController implements Reloadable{

    @FXML
    private Button enter_button;

    @FXML
    private Button create_button;

    @FXML
    private TableView<Department> DeptTable;

    @FXML
    private TableColumn<Department, String> DeptName;

    @FXML
    private TableColumn<Department, String> DeptHead;

    @FXML
    private TableColumn<Department, Integer> DeptMember;

    @FXML
    void callCreateDept(ActionEvent event) {

    }

    @FXML
    void callEnterDept(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainDept);
        reloadPage(); //could reload when change scene
        MainProgram.stageMainDeptController.reloadPage();
    }


    @Override
    public void reloadPage() {
        enter_button.setDisable(true);
    }
}


