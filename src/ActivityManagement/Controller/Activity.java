package ActivityManagement.Controller;

import javax.persistence.*;
import java.util.Vector;

@Entity
public class Activity {
    @Id
    private String actid;
    private String actname;
    private String orgname;
    private String password;
    private String actdes;
    private int active;
    private Vector<SubActivity> subact;

    public Activity(){}
    public Activity(String aid,String aname,String oname,String pass,String ades)
    {
        this.actid = aid;
        this.actname = aname;
        this.orgname = oname;
        this.password = pass;
        this.actdes = ades;
        this.active = 1;
    }

    public String getActid()
    {
        return actid;
    }
}
