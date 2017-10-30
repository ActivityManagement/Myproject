package ActivityManagement.Controller;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id @GeneratedValue
    private long id;
    private String userid;
    private String firstname;
    private String lastname;
    private String password;
    @OneToMany(fetch=FetchType.EAGER)
    private ArrayList<HasActivity> myact = new ArrayList<HasActivity>();

    public Person(){}
    public Person(String id,String pass,String fname,String lname)
    {
        this.userid = id;
        this.password = pass;
        this.firstname = fname;
        this.lastname = lname;
    }

    public String getName()
    {
        return firstname;
    }

    public String getID()
    {
        return userid;
    }

    public String getPassword()
    {
        return password;
    }

    public long getId()
    {
        return id;
    }

    public void addAct(HasActivity act)
    {
        myact.add(act);
    }

    public void setFirstname(String name)
    {
        firstname = name;
    }
}
