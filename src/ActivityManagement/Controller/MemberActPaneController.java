package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
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
    private JFXButton removeButton;

    @FXML
    private JFXButton rejectButton;

    @FXML
    private JFXButton approveButton;

    public void loadMemberTable()
    {
        joinedpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        joinedpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        joinedTable.setItems(getJoinedPerson());

        reqpidColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        reqpnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        reqTable.setItems(getRequestPerson());
    }

    public ObservableList<Person> getJoinedPerson()
    {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> jmem = MainProgram.stageMainPage.getCurrentselectact().getJoinedMember();
        for (int i = 0; i < jmem.size() ; i++) {
            p.add(jmem.get(i));
        }
        return p;
    }

    public ObservableList<Person> getRequestPerson()
    {
        ObservableList<Person> p = FXCollections.observableArrayList();
        MainProgram.updateActivity();
        ArrayList<Person> rmem = MainProgram.stageMainPage.getCurrentselectact().getRequestMember();
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

    public void setStatus(int status)
    {
        //TODO
        System.out.println(currentselectReqPerson.getFirstname());
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.DBName);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+currentselectReqPerson.getId()+"", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
            ArrayList<HasActivity> hact = p.getMyact();
            for (HasActivity ha : hact) {
                //search has act of this activity
                if (ha.getActivity().getActid().equals(MainProgram.stageMainPage.getCurrentselectact().getActid()))
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

    void disableButton()
    {
        removeButton.setDisable(true);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }
}
