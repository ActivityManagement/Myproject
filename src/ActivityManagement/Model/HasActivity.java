package ActivityManagement.Model;

import javax.persistence.*;

@Entity
public class HasActivity {
    @Id @GeneratedValue
    private long id;
    private Activity activity;
    private int approve;
    private String actstatus;
    private int role;
    /*
    status approve :
    0 = waiting
    1 = joined
    2 = rejected
     */

    /*
    role :
    0: guest
    1: member
    2: Subleader
    3: Leader
     */

    public HasActivity(){}
    public HasActivity(Activity act,int app,int role)
    {
        this.activity = act;
        setApprove(app);
        this.role = role ;
    }

    public int getApprove()
    {
        return approve;
    }

    public long getId()
    {
        return id;
    }

    public void setApprove(int value)
    {
        approve = value;
        actstatus = getStatusText();
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public String getActstatus()
    {
        return actstatus;
    }


    public String getStatusText()
    {
        if (approve==0) return "Waiting";
        else if (approve==1) return "Joined";
        else
            return "Rejected";
    }

    public Activity getActivity() {
        return activity;
    }
}
