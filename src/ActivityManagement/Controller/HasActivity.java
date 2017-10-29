package ActivityManagement.Controller;

import javax.persistence.*;

@Entity
public class HasActivity {
    @Id @GeneratedValue
    private long id;
    private Activity activity;
    private int approve;
    private Role role;


    public HasActivity(){}
    public HasActivity(Activity act,int app)
    {
        this.activity = act;
        this.approve = app;
    }

    public long getId()
    {
        return id;
    }

    public Activity getActivity() {
        return activity;
    }
}
