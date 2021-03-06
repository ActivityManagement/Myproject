package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Department;
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
    private TableColumn<Department, String> DeptNameColumn;

    @FXML
    private TableColumn<Department, String> DeptHeadColumn;


    private Department currenselectdept;

    Department getCurrentselectdept()
    {
        return currenselectdept;
    }

    @FXML
    void callCreateDept(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getCreateDept());
        MainProgram.getStageCreateDeptPage().reloadPage();
    }

    @FXML
    void callEnterDept(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainDept());
        reloadPage(); //could reload when change scene
        MainProgram.getStageMainDeptController().reloadPage();
    }


    private void LoadTable(){
        DeptNameColumn.setCellValueFactory(new PropertyValueFactory<>("DeptName"));
        DeptHeadColumn.setCellValueFactory(new PropertyValueFactory<>("DeptMasterName"));
        //DeptTable.setItems(getAllDepartment());
        DeptTable.setItems(getActivityDepartment());
    }

    public ObservableList<Department> getAllDepartment()
    {
        ObservableList<Department> dept = FXCollections.observableArrayList();
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Department> query = em.createQuery("SELECT a FROM Department a", Department.class);
        List<Department> results = query.getResultList();
        em.getTransaction().begin();
        for (Department a : results) {
            dept.add(a);
        }
        odb.closeConnection();
        return dept;
    }

    private ObservableList<Department> getActivityDepartment()
    {
        //TODO
        //Remain remove Rejected from table
        ObservableList<Department> dept = FXCollections.observableArrayList();
        //ArrayList<HasActivity> hact = MainProgram.personCurrent.getMyact();
        ArrayList<Department> hact = MainProgram.getStageMainPage().getCurrentselectact().getMyActivityDept();
        for (int i = 0; i < hact.size() ; i++) {
           // if (hact.get(i).getApprove()!=2) // except Rejected
               // activity.add(hact.get(i).getActivity());
            dept.add(hact.get(i).getDepartment());
        }
        return dept;
    }

    private void clickDeptSelect(TableView<Department> deptset)
    {
        if (!deptset.getSelectionModel().isEmpty())
        {
            currenselectdept = deptset.getSelectionModel().getSelectedItem();
            enter_button.setDisable(false);
        }
    }

    @FXML
    void clickDeptItem(MouseEvent event) {
        clickDeptSelect(DeptTable);
    }


    @Override
    public void reloadPage() {
        MainProgram.updateActivity();
//        MainProgram.updateDepartment();
        enter_button.setDisable(true);
        LoadTable();
    }
}


