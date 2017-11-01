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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MainPageController implements Reloadable {

    @FXML
    private Label useridLabel;

    @FXML
    private JFXButton join_button;

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
    private TableView<Activity> myacttable;
    @FXML
    private TableColumn<Activity, String> myactidColumn;
    @FXML
    private TableColumn<Activity, String> myactnameColumn;
    @FXML
    private TableColumn<Activity, String> myactorColumn;
    @FXML
    private TableColumn<Activity, String> myactdescColumn;
    @FXML
    private TableColumn<Activity, String> myactstatusColumn;

    @FXML
    private StackPane joinPane;
    @FXML
    private JFXDialogLayout contentofjoin;

    private JFXDialog joindialog = null;

    private Activity currentselectact;


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

    @FXML
    void callJoinEvent(ActionEvent event) {
        //TODO
        joinPane.setVisible(true);
        if (joindialog==null) {
            joindialog = new JFXDialog(joinPane, contentofjoin, JFXDialog.DialogTransition.CENTER);
        }
        joindialog.show();
        System.out.println(currentselectact.getActname());
    }

    @FXML
    void callCancelJoin(MouseEvent event) {
        joinPane.setVisible(false);
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

        myactidColumn.setCellValueFactory(new PropertyValueFactory<>("actid"));
        myactnameColumn.setCellValueFactory(new PropertyValueFactory<>("actname"));
        myactorColumn.setCellValueFactory(new PropertyValueFactory<>("orgname"));
        myactdescColumn.setCellValueFactory(new PropertyValueFactory<>("actdes"));
        myactstatusColumn.setCellValueFactory(new PropertyValueFactory<>("actstatus"));
        myacttable.setItems(getMyActivity());

//        acttable.setRowFactory( tv -> {
//            TableRow<Activity> arow = new TableRow<>();
//            arow.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (! arow.isEmpty()) ) {
//                    System.out.println(arow.getItem().getActname());
//                }
//            });
//            return arow ;
//        });

    }

    public ObservableList<Activity> getMyActivity()
    {
        ObservableList<Activity> activity = FXCollections.observableArrayList();
        ArrayList<HasActivity> hact = MainProgram.personCurrent.getMyact();
        for (int i = 0; i < hact.size() ; i++) {
            activity.add(hact.get(i).getActivity());
        }
        return activity;
    }

    @FXML
    void clickActItem(MouseEvent event) {

        if (!acttable.getSelectionModel().isEmpty())
        {
            currentselectact = acttable.getSelectionModel().getSelectedItem();
            join_button.setDisable(false);
            if (!myacttable.getSelectionModel().isEmpty()) //if another table had selected
            {
                myacttable.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    void clickMyActItem(MouseEvent event) {

        if (!myacttable.getSelectionModel().isEmpty())
        {
            currentselectact = myacttable.getSelectionModel().getSelectedItem();
            join_button.setDisable(false);
            if (!acttable.getSelectionModel().isEmpty()) //if another table had selected
            {
                acttable.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    void callRefreshTable(ActionEvent event) {
        loadTableActivity();
        currentselectact = null;
        join_button.setDisable(true);
        joinPane.setVisible(false);
    }


    @Override
    public void reloadPage() {
        loadTableActivity();
        useridLabel.setText(MainProgram.personCurrent.getUserid());
        fnameLabel.setText(MainProgram.personCurrent.getFirstname());
        lnameLabel.setText(MainProgram.personCurrent.getLastname());
        currentselectact = null;
        join_button.setDisable(true);
        joinPane.setVisible(false);
    }
}
