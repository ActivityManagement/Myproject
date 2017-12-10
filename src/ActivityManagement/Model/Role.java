package ActivityManagement.Model;

import javax.persistence.Entity;

public class Role {

    private int Rolelevel ;
    private String levelname ;
    public Role() {
    }

    public Role(int rolelevel) {
        Rolelevel = rolelevel;
        if(Rolelevel == 3)
        {
            levelname = "Leader" ;
        }
        else if (Rolelevel == 2)
        {
            levelname = "SubLeader" ;
        }
        else if (Rolelevel == 1)
            levelname = "Member" ;
        else
            levelname = "Guest" ;
    }

    public int getRolelevel() {
        return Rolelevel;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setRolelevel(int rolelevel) {
        Rolelevel = rolelevel;
        if(Rolelevel == 3)
        {
            levelname = "Leader" ;
        }
        else if (Rolelevel == 2)
        {
            levelname = "SubLeader" ;
        }
        else if (Rolelevel == 1)
            levelname = "Member" ;
        else
            levelname = "Guest" ;
    }
}
