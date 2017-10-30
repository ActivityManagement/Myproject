package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MainPageController implements Reloadable {

    @FXML
    private Label fnameLabel;

    @FXML
    private Label lnameLabel;

    @FXML
    private TableView<Activity> acttable;

    @FXML
    private TableColumn<Activity, String> actidColumn;

    @FXML
    private TableColumn<Activity, String> actnameColumn;

    @FXML
    private TableColumn<Activity, String> actorColumn;

    @FXML
    private TableColumn<Activity, String> actdescColumn;


    @FXML
    void callCreateAct(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.createact);
        reloadPage();

    }

    @FXML
    void callLogout(ActionEvent event) {
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.login);
        reloadPage();

    }

    public void setFnameLabel(String name)
    {
        fnameLabel.setText(name);
    }
    public void setLnameLabel(String name)
    {
        lnameLabel.setText(name);
    }

    public ObservableList<Activity> getAllActivity()
    {
        ObservableList<Activity> activity = FXCollections.observableArrayList();
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.DBName);
        TypedQuery<Activity> query = em.createQuery("SELECT a FROM Activity a", Activity.class);
        List<Activity> results = query.getResultList();
        em.getTransaction().begin();
        for (Activity a : results) {
            activity.add(a);
        }
        odb.closeConnection();
        return activity;
    }

    public void loadTableActivity()
    {
        actidColumn.setCellValueFactory(new PropertyValueFactory<>("actid"));
        actnameColumn.setCellValueFactory(new PropertyValueFactory<>("actname"));
        actorColumn.setCellValueFactory(new PropertyValueFactory<>("orgname"));
        actdescColumn.setCellValueFactory(new PropertyValueFactory<>("actdes"));
        acttable.setItems(getAllActivity());
    }

    @FXML
    void callRefreshTable(ActionEvent event) {
        loadTableActivity();

    }

    @Override
    public void reloadPage() {
        loadTableActivity();

    }
}
