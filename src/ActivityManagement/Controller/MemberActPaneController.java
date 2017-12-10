package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
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

public class MemberActPaneController implements Reloadable {

    @FXML
    private TableView<Person> joinedTable;

    @FXML
    private TableColumn<Person, String> joinedpidColumn;

    @FXML
    private TableColumn<Person, String> joinedpnameColumn;

    @FXML
    private TableColumn<HasActivity, String> RoleColumn;

    @FXML
    private TableView<Person> reqTable;

    @FXML
    private TableColumn<Person, String> reqpidColumn;

    @FXML
    private TableColumn<Person, String> reqpnameColumn;

    private Person currentselectReqPerson;

    @FXML
    private Person currentselectJoinedPerson;

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
        LeaderRadio.setSelected(true);
        SubLeaderRadio.setSelected(false);
        MemberRadio.setSelected(false);
    }

    @FXML
    void SetPermToMember(ActionEvent event) {
        LeaderRadio.setSelected(false);
        SubLeaderRadio.setSelected(false);
        MemberRadio.setSelected(true);
    }

    @FXML
    void SetPermToSubLeader(ActionEvent event) {
        LeaderRadio.setSelected(false);
        SubLeaderRadio.setSelected(true);
        MemberRadio.setSelected(false);
    }

    @FXML
    void SetPermission(ActionEvent event) {

        if (currentselectJoinedPerson != null) {

            //.setText(currentselectJoinedPerson.getId().toString());
            NameLabel.setText(currentselectJoinedPerson.getFirstname());
            SetPermissionPane.setVisible(true);
            System.out.println("Test1");
            if (SetPermDialog == null) {
                SetPermDialog = new JFXDialog(SetPermissionPane, contentofPerm, JFXDialog.DialogTransition.CENTER);
                System.out.println("Test2");
            }
            SetPermDialog.show();
            System.out.println("Test3");
        }
    }

    @FXML
    void callcancelPerm(ActionEvent event) {

        closeAllDialogPane();
    }

    @FXML
    void callsubmitPerm(ActionEvent event) {

        if (LeaderRadio.isSelected() == true) {
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + currentselectJoinedPerson.getId() + "", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                        ha.setRole(3);
                    }
                }
                currentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        if (SubLeaderRadio.isSelected() == true) {
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + currentselectJoinedPerson.getId() + "", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                        ha.setRole(2);
                    }
                }
                currentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        if (MemberRadio.isSelected() == true) {
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + currentselectJoinedPerson.getId() + "", Person.class);
            List<Person> results = query.getResultList();
            em.getTransaction().begin();
            for (Person p : results) {
                ArrayList<HasActivity> hact = p.getMyact();
                for (HasActivity ha : hact) {
                    //search has act of this activity
                    if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                        ha.setRole(1);
                    }
                }
                currentselectJoinedPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        SetPermDialog.close();
        SetPermissionPane.setVisible(false);
        reloadPage();
    }


    private void loadMemberTable() {
        joinedpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        joinedpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        //RoleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
        joinedTable.setItems(getJoinedPerson());

        reqpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        reqpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        reqTable.setItems(getRequestPerson());
    }

    private ObservableList<Person> getJoinedPerson() {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> jmem = MainProgram.getStageMainPage().getCurrentselectact().getJoinedMember();
        for (int i = 0; i < jmem.size(); i++) {
            p.add(jmem.get(i));
        }
        return p;
    }

    private ObservableList<Person> getRequestPerson() {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> rmem = MainProgram.getStageMainPage().getCurrentselectact().getRequestMember();
        for (int i = 0; i < rmem.size(); i++) {
            p.add(rmem.get(i));
        }
        return p;
    }


    @FXML
    void clickSelectPerson(MouseEvent event) {
        // check if don't click null
        if (!reqTable.getSelectionModel().isEmpty()) {
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
        if (!joinedTable.getSelectionModel().isEmpty()) {
            currentselectJoinedPerson = joinedTable.getSelectionModel().getSelectedItem();
            SetPermButton.setDisable(false);
            if (!reqTable.getSelectionModel().isEmpty()) //if another table had selected
            {
                disableButton();

                reqTable.getSelectionModel().clearSelection();
            }
        }
    }


    @FXML
    void callApproveSelect(ActionEvent event) {
        if (currentselectReqPerson != null) {
            setStatus(1, 1);
        }
        reloadPage();
    }

    @FXML
    void callRejectMember(ActionEvent event) {
        if (currentselectReqPerson != null) {
            setStatus(2, 0);
        }
        reloadPage();
    }
    @FXML
    private JFXTabPane MemberTabPane;
    @FXML
    private Tab RequestTab;

    private void setStatus(int status, int role) {
        //TODO
        System.out.println(currentselectReqPerson.getFirstname());
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.getDBName());
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = " + currentselectReqPerson.getId() + "", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
            ArrayList<HasActivity> hact = p.getMyact();
            for (HasActivity ha : hact) {
                //search has act of this activity
                if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                    ha.setApprove(status);
                    ha.setRole(role);
                }
            }
            currentselectReqPerson = p;
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }

    private void disableButton() {
        removeButton.setDisable(true);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
        SetPermButton.setDisable(true);
    }

    private void closeAllDialogPane()
    {
        SetPermissionPane.setVisible(false);
        if (SetPermDialog != null)
            SetPermDialog.close();

    }

    @Override
    public void reloadPage() {
        int checkrole = 0;
        MainProgram.updatePerson();
        MainProgram.updateActivity();
        ArrayList<HasActivity> hact = MainProgram.getPersonCurrent().getMyact();
        for (HasActivity ha : hact) {
            //search has act of this activity
            if (ha.getActivity().getActid().equals(MainProgram.getStageMainPage().getCurrentselectact().getActid())) {
                checkrole = ha.getRole();
                break;
            }
        }
        System.out.println(checkrole);

        if (checkrole == 3) {

            disableButton();
            loadMemberTable();
            reqTable.setDisable(false);
            joinedTable.setDisable(false);
            MemberTabPane.getTabs().remove(RequestTab);
            MemberTabPane.getTabs().add(RequestTab);
        } else if (checkrole == 2) {
            disableButton();
            loadMemberTable();
            reqTable.setDisable(true);
            joinedTable.setDisable(true);
            MemberTabPane.getTabs().remove(RequestTab);
            MemberTabPane.getTabs().add(RequestTab);
        } else {
            disableButton();
            loadMemberTable();
            reqTable.setDisable(true);
            joinedTable.setDisable(true);
            MemberTabPane.getTabs().remove(RequestTab);
        }


    }

}
