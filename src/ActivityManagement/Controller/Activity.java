package ActivityManagement.Controller;

import ActivityManagement.MainProgram;

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

    //get act status for person in column
    public String getActstatus()
    {
        ArrayList<HasActivity> hact = MainProgram.personCurrent.getMyact();
        for (int i = 0; i < hact.size() ; i++) {
            if (actid.equals(hact.get(i).getActivity().getActid()))
            {
                return hact.get(i).getActstatus();
            }
        }
        return "Valid";
    }

}
