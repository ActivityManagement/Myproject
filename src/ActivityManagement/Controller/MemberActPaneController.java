package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

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
        ArrayList<Person> jmem = MainProgram.stageMainPage.getCurrentselectact().getJoinedMember();
        for (int i = 0; i < jmem.size() ; i++) {
            p.add(jmem.get(i));
        }
        return p;
    }

    public ObservableList<Person> getRequestPerson()
    {
        ObservableList<Person> p = FXCollections.observableArrayList();
        ArrayList<Person> rmem = MainProgram.stageMainPage.getCurrentselectact().getRequestMember();
        for (int i = 0; i < rmem.size() ; i++) {
            p.add(rmem.get(i));
        }
        return p;
    }

    @Override
    public void reloadPage() {
        loadMemberTable();
    }
}
