package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.DBConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateActController {
    @FXML
    private JFXTextField actname_box;
    @FXML
    private JFXTextField orgname_box;
    @FXML
    private JFXTextField password_box;
    @FXML
    private Label create_status;

    private final String tableName = "Activitylist";
    private final String actdbsql = "Create TABLE "+tableName+" (AID varchar(6),ActName varchar(50),OrgName varchar(50),ActPassword varchar(16),Active INT)";

    @FXML
    void clickBackButton(ActionEvent event) {
        MainProgram.primaryWindow.setScene(MainProgram.loginScene);
    }

    @FXML
    void clickClearButton(ActionEvent event) {
        actname_box.clear();
        orgname_box.clear();
        password_box.clear();

    }

    @FXML
    void clickConfirmButton(ActionEvent event) {
        String actname = actname_box.getText();
        String orgname = orgname_box.getText();
        String password = password_box.getText();
        boolean check = false;
        check = checkName(actname)&&checkOrg(orgname)&&checkPassword(password);

        if (check)
        {
            //TODO
            create_status.setText("ถูกแล้วจ้าาา");
            DBConnection bconnection = new DBConnection();
            bconnection.createTable(actdbsql);
            int actid;
            String sgetid = bconnection.getLastValue(tableName,"AID");
            //if the first activity
            if (sgetid==null)
            {
                actid = 0;
            }
            else
            {
                actid = Integer.parseInt(sgetid);
                actid++;
            }
            String sActid = String.format("%06d",actid);
            bconnection.insertToTable("INSERT INTO "+tableName+" Values ('"+sActid+"','"+actname+"','"+orgname+"','"+password+"',"+1+")");
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
}