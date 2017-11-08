package ActivityManagement.Controller;

import javax.persistence.*;

@Entity
public class HasActivity {
    @Id @GeneratedValue
    private long id;
    private Activity activity;
    private int approve;
    private String actstatus;
    private Role role;
    /*
    status approve :
    0 = waiting
    1 = joined
    2 = rejected
     */


    public HasActivity(){}
    public HasActivity(Activity act,int app)
    {
        this.activity = act;
        setApprove(app);
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
