package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.*;
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

    @FXML
    private PasswordField JoinPassField;


    @FXML
    private Label joinActID;

    @FXML
    private Label joinActName;

    @FXML
    private Label joinOrgName;

    @FXML
    private Label statusjointext;

    @FXML
    private StackPane waitPane;

    @FXML
    private JFXDialogLayout contentofwaiting;

    private JFXDialog waitdialog = null;

    private JFXDialog joindialog = null;

    private Activity currentselectact;


    public Activity getCurrentselectact()
    {
        return currentselectact;
    }

    public void setCurrentselectact(Activity a)
    {
        currentselectact = a;
    }

    @FXML
    void callCreateAct(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getCreateact());
        reloadPage();
    }

    @FXML
    void callLogout(ActionEvent event) {
        MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getLogin());
        reloadPage();
    }

    @FXML
    void callcanceljoinAct(ActionEvent event) {
        closeAllDialogPane();
    }

    @FXML
    void callsubmitjoinAct(ActionEvent event) {

        //check join act password
        String pass = JoinPassField.getText();
        if (pass.equals(currentselectact.getPassword()))
        {
            // join act when pass valid
            //TODO
            // check if join again
            boolean found = false;
            MainProgram.updatePerson();
            MainProgram.updateActivity();
            ArrayList<HasActivity> myact = MainProgram.getPersonCurrent().getMyact();
            for (HasActivity ha : myact) {
                // check if has act
                if (ha.getActivity().getActid().equals(currentselectact.getActid()))
                {
                    found = true;
                }
            }

            if (found)
            {
                //TODO
                ObjectDB odb = new ObjectDB();
                EntityManager em = odb.createConnection(MainProgram.getDBName());
                TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + MainProgram.getPersonCurrent().getId() + "", Person.class);
                List<Person> results = query.getResultList();
                em.getTransaction().begin();
                for (Person p : results) {
                    ArrayList<HasActivity> hact = p.getMyact();
                    for (HasActivity ha : hact) {
                        //search has act of this activity
                        if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid()))
                        {
                            ha.setApprove(0);
                        }
                    }
                    MainProgram.setPersonCurrent(p);
                }
                em.getTransaction().commit();
                odb.closeConnection();
            }
            else {
                ObjectDB odb = new ObjectDB();
                odb.createConnection(MainProgram.getDBName());
                HasActivity hact = new HasActivity(currentselectact, 0,0);
                odb.saveObject(hact);
                odb.closeConnection();
                //-------------------------- update hasact in person--------------------------
                EntityManager em = odb.createConnection(MainProgram.getDBName());
                TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + MainProgram.getPersonCurrent().getId() + "", Person.class);
                List<Person> results = query.getResultList();
                em.getTransaction().begin();
                for (Person p : results) {
                    p.addAct(hact);
                    MainProgram.setPersonCurrent(p);
                }
                em.getTransaction().commit();
                //----------------------------------------------------------------------------
                //-------------------------- update member to activity--------------------------
                em = odb.createConnection(MainProgram.getDBName());
                TypedQuery<Activity> actquery = em.createQuery("SELECT a FROM Activity a where a.actid = '" + currentselectact.getActid() + "'", Activity.class);
                List<Activity> actresults = actquery.getResultList();
                em.getTransaction().begin();
                for (Activity a : actresults) {
                    a.addMember(MainProgram.getPersonCurrent());
                    currentselectact = a;
                }
                em.getTransaction().commit();
                //-----------------------------------------------------------------------------
                odb.closeConnection();
            }
            MainProgram.updateActivity();
            MainProgram.updatePerson();
            joindialog.close();
            joinPane.setVisible(false);
            JoinPassField.clear();
            reloadPage();
        }
        else //when password is false
        {
            statusjointext.setText("*Password invalid ");
        }

    }

    @FXML
    void callJoinEvent(ActionEvent event) {
        //add condition
        if (isDoNotJoin(currentselectact)) //don't join yet
        {
            joinActID.setText(currentselectact.getActid());
            joinActName.setText(currentselectact.getActname());
            joinOrgName.setText(currentselectact.getOrgname());
            joinPane.setVisible(true);
            if (joindialog == null) {
                joindialog = new JFXDialog(joinPane, contentofjoin, JFXDialog.DialogTransition.CENTER);
            }
            joindialog.show();
        }
        else //in hasActivity
        {
            if (isApprove(currentselectact))
            {
                //TODO
                MainProgram.getPrimaryWindow().getScene().setRoot(MainProgram.getMainactpage());
                MainProgram.getStageMainActPage().reloadPage();
                System.out.println("gogo");
            }
            else //wait to approve
            {
                waitPane.setVisible(true);
                if (waitdialog == null) {
                    waitdialog = new JFXDialog(waitPane, contentofwaiting, JFXDialog.DialogTransition.CENTER);
                }
                waitdialog.show();
                System.out.println("waiting");
            }
        }
    }

    private boolean isDoNotJoin(Activity a)
    {
        ArrayList<HasActivity> hact = MainProgram.getPersonCurrent().getMyact();
        for (int i = 0; i < hact.size() ; i++) {
            if (a.getActid().equals(hact.get(i).getActivity().getActid()))
            {
                if (hact.get(i).getApprove()!=2) // if not rejected
                    return false;
            }
        }
        return true;
    }

    private boolean isApprove(Activity a)
    {
        ArrayList<HasActivity> hact = MainProgram.getPersonCurrent().getMyact();
        for (int i = 0; i < hact.size() ; i++) {
            if (a.getActid().equals(hact.get(i).getActivity().getActid()))
            {
                if (hact.get(i).getApprove()==1) {
                    return true;
                }
            }
        }
        return false;
    }

    //cancle stack pane with click on pane
    @FXML
    void callCancelJoin(MouseEvent event) {
        closeAllDialogPane();
    }

    private void closeAllDialogPane()
    {
        joinPane.setVisible(false);
        waitPane.setVisible(false);
        if (joindialog != null)
            joindialog.close();
        if (waitdialog != null)
            waitdialog.close();
        JoinPassField.clear();
        statusjointext.setText("");
    }

    private ObservableList<Activity> getAllActivity()
    {
        ObservableList<Activity> activity = FXCollections.observableArrayList();
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Activity> query = em.createQuery("SELECT a FROM Activity a", Activity.class);
        List<Activity> results = query.getResultList();
        em.getTransaction().begin();
        for (Activity a : results) {
            activity.add(a);
        }
        odb.closeConnection();
        return activity;
    }

    private void loadTableActivity()
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

    private ObservableList<Activity> getMyActivity()
    {
        //TODO
        //Remain remove Rejected from table
        ObservableList<Activity> activity = FXCollections.observableArrayList();
        ArrayList<HasActivity> hact = MainProgram.getPersonCurrent().getMyact();
        for (int i = 0; i < hact.size() ; i++) {
            if (hact.get(i).getApprove()!=2) // except Rejected
                activity.add(hact.get(i).getActivity());
        }
        return activity;
    }

    private void clickActSelect(TableView<Activity> actset,TableView<Activity> actreset)
    {
        if (!actset.getSelectionModel().isEmpty())
        {
            currentselectact = actset.getSelectionModel().getSelectedItem();
            join_button.setDisable(false);
            //TODO
            // edit disable join button when waiting to join
//            if (currentselectact.getActstatus().equals("Waiting"))
//                join_button.setDisable(true);

            if (!actreset.getSelectionModel().isEmpty()) //if another table had selected
            {
                actreset.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    void clickActItem(MouseEvent event) {
        clickActSelect(acttable,myacttable);
    }

    @FXML
    void clickMyActItem(MouseEvent event) {
        clickActSelect(myacttable,acttable);
    }

    @FXML
    void callRefreshTable(ActionEvent event) {
        MainProgram.updatePerson();
        loadTableActivity();
        reloadPage();
    }

    @Override
    public void reloadPage() {
        loadTableActivity();
        useridLabel.setText(MainProgram.getPersonCurrent().getUserid());
        fnameLabel.setText(MainProgram.getPersonCurrent().getFirstname());
        lnameLabel.setText(MainProgram.getPersonCurrent().getLastname());
        currentselectact = null;
        join_button.setDisable(true);
        statusjointext.setText("");
        joinPane.setVisible(false);
        JoinPassField.clear();
    }

}
