package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class CreateActController implements Reloadable {
    @FXML
    private JFXTextField actname_box;
    @FXML
    private JFXTextField orgname_box;
    @FXML
    private JFXTextField password_box;
    @FXML
    private JFXTextField descript_box;
    @FXML
    private Label create_status;

    @FXML
    void clickBackButton(ActionEvent event) {
        //TODO
        MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainpage);
        reloadPage(); //could reload when change scene
    }

    @FXML
    void clickClearButton(ActionEvent event) {
        reloadPage();
    }

    @FXML
    void clickConfirmButton(ActionEvent event) {
        String actname = actname_box.getText();
        String orgname = orgname_box.getText();
        String password = password_box.getText();
        String desc = descript_box.getText();
        boolean check = false;
        check = checkName(actname)&&checkOrg(orgname)&&checkPassword(password);

        if (check)
        {
            //TODO
//            create_status.setText("ถูกแล้วจ้าาา");
            String actid = null;
            ObjectDB odb = new ObjectDB();
            EntityManager em = odb.createConnection(MainProgram.DBName);
            if (!odb.isRecordExist("Activity")) // check if does't exists any act
                actid = "000000";
            else
            {
                int cid = 0;
                TypedQuery<Activity> query = em.createQuery("SELECT a FROM Activity a", Activity.class);
                List<Activity> results = query.getResultList();
                for (Activity a : results) {
                    cid = Integer.parseInt(a.getActid());
                }
                actid = String.format("%06d",cid+1);
            }
            Activity act = new Activity(actid,actname,orgname,password,desc);
            odb.saveObject(act);
            odb.closeConnection();
            MainProgram.primaryWindow.getScene().setRoot(MainProgram.mainpage);
        }
    }

    private boolean checkName(String actname)
    {
        if (actname.length()==0)
        {
            create_status.setText("กรุณาใส่ชื่อกิจกรรม");
            return false;
        }
        return true;
    }

    private boolean checkOrg(String orgname)
    {
        if (orgname.length()==0)
        {
            create_status.setText("กรุณาใส่ชื่อองค์กร");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password)
    {
        boolean value = false;
        if (password.length() < 8)
            create_status.setText("รหัสกิจกรรมต้องมีความยาวอย่างน้อย 8 ตัว");
        else if (password.length() > 16)
            create_status.setText("รหัสกิจกรรมต้องไม่เกิน 16 ตัว");
        else if (!truePassword(password))
            create_status.setText("รหัสผ่านต้องประกอบด้วยทุกรูปแบบใน a-z, A-Z, 0-9 เท่านั้น");
        else
            value = true;
        return value;
    }

    private boolean truePassword(String password)
    {
        boolean hascapital = false;
        boolean hasletter = false;
        boolean hasnumber = false;

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                hascapital = true;
            else if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                hasletter = true;
            else if(password.charAt(i) >= '0' && password.charAt(i) <= '9')
                hasnumber = true;
            else
                return false;
        }
        return hascapital&&hasletter&&hasnumber;
    }

    public void reloadPage()
    {
        actname_box.clear();
        orgname_box.clear();
        password_box.clear();
        descript_box.clear();
        create_status.setText("");
    }
}
