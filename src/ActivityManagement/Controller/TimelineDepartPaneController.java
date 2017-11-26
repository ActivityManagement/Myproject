package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.Department;
import ActivityManagement.Model.ObjectDB;
import ActivityManagement.Model.TimeItem;
import ActivityManagement.Model.Timeline;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimelineDepartPaneController implements Reloadable {

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXButton add_button;

    @FXML
    private JFXTextField detailInput;

    private Timeline currentTimelineDate;

    @FXML
    private TableView<TimeItem> timelineTable;

    @FXML
    private TableColumn<TimeItem, String> coltime;

    @FXML
    private TableColumn<TimeItem, String> coldetail;



    @FXML
    void callSelectDate(ActionEvent event) {
        if (datePicker.getValue()!=null) {
            currentTimelineDate = getTimelineDate(datePicker.getValue());
            loadTimelineTable();
        }
        checkAddButton();

    }

    private void loadTimelineTable()
    {
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        coldetail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        timelineTable.setItems(getTimeItemList());
        timelineTable.getSortOrder().add(coltime);
    }

    private ObservableList<TimeItem> getTimeItemList()
    {
        MainProgram.updateDepartment();
        Department dept = MainProgram.getStageDeptPane().getCurrentselectdept();
        ObservableList<TimeItem> list = FXCollections.observableArrayList();
        for (Timeline t:dept.getTimelines()) {
            if (t.getDate().equals(currentTimelineDate.getDate()))
                list.addAll(t.getItem());
        }
        return list;
    }

    @FXML
    void callSelectTime(ActionEvent event) {
        checkAddButton();

    }

    public void reloadPage()
    {
        MainProgram.updateDepartment();
        currentTimelineDate = null;
        timelineTable.getItems().clear();
        timePicker.setIs24HourView(true);
        datePicker.setValue(null);
        timePicker.setValue(null);
        detailInput.clear();
        checkAddButton();
    }

    private void checkAddButton()
    {
        if (datePicker.getValue()!=null && timePicker.getValue()!=null && !detailInput.getText().trim().isEmpty()) {
            add_button.setDisable(false);
        }
        else {
            add_button.setDisable(true);
        }
    }

    private Timeline getTimelineDate(LocalDate date)
    {
        MainProgram.updateDepartment();
        Timeline currenttl = null;
        Department dept = MainProgram.getStageDeptPane().getCurrentselectdept();
        ArrayList<Timeline> gtimelines = dept.getTimelines();
        for (Timeline tl : gtimelines) {
            if (tl.getDate().equals(date.toString())) //if have timeline in this department
            {
                currenttl = tl;
            }
        }
        if (currenttl==null) //don't have timelinedate in this department
        {
            currenttl = new Timeline(date);
            System.out.println("New Timeline");
        }
        return currenttl;
    }

    @FXML
    void clickAddTimeButton(ActionEvent event) {
        if (datePicker.getValue()!=null && timePicker.getValue()!=null && !detailInput.getText().trim().isEmpty()) {
            currentTimelineDate = getTimelineDate(datePicker.getValue());
            Department dept = MainProgram.getStageDeptPane().getCurrentselectdept();
            TimeItem ti = new TimeItem(timePicker.getValue().toString(), detailInput.getText());
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.getDBName());
            TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d WHERE d.id = " + dept.getId() + "", Department.class);
            List<Department> result = query.getResultList();
            em.getTransaction().begin();
            for (Department d : result) {
                boolean isfound = false;
                for (Timeline t: d.getTimelines()) {
                    if (t.getDate().equals(currentTimelineDate.getDate())) {
                        isfound=true;
                        t.addTime(ti);
                    }
                }
                if (!isfound)
                {
                    currentTimelineDate.addTime(ti);
                    d.addTimeline(currentTimelineDate);
                }
                MainProgram.getStageDeptPane().setCurrenselectdept(d);
            }
            em.getTransaction().commit();
            odb.closeConnection();
            reloadPage();
        }
    }

    @FXML
    void inputToText(KeyEvent event) {
        checkAddButton();
    }
}
