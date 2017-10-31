package ActivityManagement.Controller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {
    @Id
    private String actid;
    private String actname;
    private String orgname;
    private String password;
    private String actdes;
    private int active;
    private String actstatus;
    @OneToMany(fetch=FetchType.EAGER)
    private ArrayList<SubActivity> subact = new ArrayList<SubActivity>();;

    public Activity(){}
    public Activity(String aid,String aname,String oname,String pass,String ades)
    {
        this.actid = aid;
        this.actname = aname;
        this.orgname = oname;
        this.password = pass;
        this.actdes = ades;
        this.active = 1;
        actstatus = getStatusText();
    }

    public String getActid()
    {
        return actid;
    }

    public String getActname()
    {
        return actname;
    }

    public String getOrgname()
    {
        return orgname;
    }

    public String getActdes()
    {
        return actdes;
    }

    public String getActstatus()
    {
        return actstatus;
    }

    public String getStatusText()
    {
        if (active==0) return "Waiting";
        return "Joined";
    }
}
