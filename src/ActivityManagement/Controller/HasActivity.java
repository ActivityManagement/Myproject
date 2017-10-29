package ActivityManagement.Controller;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HasActivity {
    @Id
    private Activity activity;
    private int approve;
    private Role role;
}
