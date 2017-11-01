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


    public HasActivity(){}
    public HasActivity(Activity act,int app)
    {
        this.activity = act;
        this.approve = app;
        actstatus = getStatusText();
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
    }

    public String getActstatus()
    {
        return actstatus;
    }


    public String getStatusText()
    {
        if (approve==0) return "Waiting";
        return "Joined";
    }

    public Activity getActivity() {
        return activity;
    }
}
