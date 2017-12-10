package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.HasActivity;
import ActivityManagement.Model.Leader;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.Person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.persistence.EntityManager;
import javax.persistence.JoinTable;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MemberActPaneController implements Reloadable{

    @FXML
    private TableView<Person> joinedTable;

    @FXML
    private TableColumn<Person, String> joinedpidColumn;

    @FXML
    private TableColumn<Person, String> joinedpnameColumn;

    @FXML
    private TableView<Person> reqTable;

    @FXML
    private TableColumn<Person, String> reqpidColumn;

    @FXML
    private TableColumn<Person, String> reqpnameColumn;

    private Person currentselectReqPerson;

    @FXML
    private Person currrentselectJoinedPerson;

    @FXML
    private JFXButton removeButton;

    @FXML
    private JFXButton rejectButton;

    @FXML
    private JFXButton approveButton;

    @FXML
    private JFXButton SetPermButton;

    @FXML
    private RadioButton LeaderRadio;

    @FXML
    private RadioButton SubLeaderRadio;

    @FXML
    private RadioButton MemberRadio;


    @FXML
    private Label PersonIDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private StackPane SetPermissionPane;

    @FXML
    private JFXDialog SetPermDialog = null;

    @FXML
    private JFXDialogLayout contentofPerm;

    @FXML
    void SetPermToLeader(ActionEvent event) {
        SubLeaderRadio.setSelected(false);
        MemberRadio.setSelected(false);
    }

    @FXML
    void SetPermToMember(ActionEvent event) {
        LeaderRadio.setSelected(false);
        MemberRadio.setSelected(false);
    }

    @FXML
    void SetPermToSubLeader(ActionEvent event) {
        LeaderRadio.setSelected(false);
        SubLeaderRadio.setSelected(false);
    }

    @FXML
    void SetPermission(ActionEvent event) {
        if (currrentselectJoinedPerson!=null) {
                PersonIDLabel.setText(String.valueOf(currrentselectJoinedPerson.getId()));
                NameLabel.setText(currrentselectJoinedPerson.getFirstname());
                SetPermissionPane.setVisible(true);
                if (SetPermDialog == null) {
                    SetPermDialog = new JFXDialog(SetPermissionPane,contentofPerm , JFXDialog.DialogTransition.CENTER);
                }
            SetPermDialog.show();
        }
    }

    @FXML
    void callcancelPerm(ActionEvent event) {

        SetPermDialog.close();
        SetPermissionPane.setVisible(false);
    }

    @FXML
    void callsubmitPerm(ActionEvent event) {

        if(LeaderRadio.isSelected() == true ){
            System.out.println(currrentselectJoinedPerson.getFirstname());
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+currrentselectJoinedPerson.getId()+"", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid()))
                    {
                        ha.setRole(3);
                    }
                }
                currrentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        if(SubLeaderRadio.isSelected() == true ){
            System.out.println(currrentselectJoinedPerson.getFirstname());
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+currrentselectJoinedPerson.getId()+"", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid()))
                    {
                        ha.setRole(2);
                    }
                }
                currrentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        if(MemberRadio.isSelected() == true ){
            System.out.println(currrentselectJoinedPerson.getFirstname());
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+currrentselectJoinedPerson.getId()+"", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid()))
                    {
                        ha.setRole(1);
                    }
                }
                currrentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        SetPermDialog.close();
        SetPermissionPane.setVisible(false);
    }


    private void loadMemberTable()
    {
        joinedpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        joinedpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        joinedTable.setItems(getJoinedPerson());

        reqpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        reqpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        reqTable.setItems(getRequestPerson());
    }

    private ObservableList<Person> getJoinedPerson()
    {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> jmem = MainProgram.getStageMainPage().getCurrentselectact().getJoinedMember();
        for (int i = 0; i < jmem.size() ; i++) {
            p.add(jmem.get(i));
        }
        return p;
    }

    private ObservableList<Person> getRequestPerson()
    {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> rmem = MainProgram.getStageMainPage().getCurrentselectact().getRequestMember();
        for (int i = 0; i < rmem.size() ; i++) {
            p.add(rmem.get(i));
        }
        return p;
    }


    @FXML
    void clickSelectPerson(MouseEvent event) {
        // check if don't click null
        if (!reqTable.getSelectionModel().isEmpty())
        {
            approveButton.setDisable(false);
            rejectButton.setDisable(false);
            currentselectReqPerson = reqTable.getSelectionModel().getSelectedItem();
            if (!joinedTable.getSelectionModel().isEmpty()) //if another table had selected
            {
                joinedTable.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    void clickSelectJoinedMember(MouseEvent event) {
        if (!joinedTable.getSelectionModel().isEmpty())
        {
            if (!reqTable.getSelectionModel().isEmpty()) //if another table had selected
            {
                disableButton();
                reqTable.getSelectionModel().clearSelection();
            }
        }
    }


    @FXML
    void callApproveSelect(ActionEvent event) {
        if (currentselectReqPerson!=null) {
            setStatus(1);
        }
        reloadPage();
    }

    @FXML
    void callRejectMember(ActionEvent event) {
        if (currentselectReqPerson!=null) {
            setStatus(2);
        }
        reloadPage();
    }

    private void setStatus(int status)
    {
        //TODO
        System.out.println(currentselectReqPerson.getFirstname());
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+currentselectReqPerson.getId()+"", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
            ArrayList<HasActivity> hact = p.getMyact();
            for (HasActivity ha : hact) {
                //search has act of this activity
                if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid()))
                {
                    ha.setApprove(status);
                }
            }
            currentselectReqPerson = p;
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }

    @Override
    public void reloadPage() {
        MainProgram.updatePerson();
        MainProgram.updateActivity();
        disableButton();
        loadMemberTable();
    }

    private void disableButton()
    {
        removeButton.setDisable(true);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
        SetPermButton.setDisable(true);
    }
}
