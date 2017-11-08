package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
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
            currentselectReqPerson = reqTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    void callApproveSelect(ActionEvent event) {
        if (currentselectReqPerson!=null) {
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
                        ha.setApprove(1);
                    }
                }
                currentselectReqPerson = p;
            }
            em.getTransaction().commit();
            odb.closeConnection();
        }
        reloadPage();
    }

    @Override
    public void reloadPage() {
        loadMemberTable();
    }
}
