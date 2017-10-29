package ActivityManagement.Controller;

import javax.persistence.*;

import java.util.Vector;

@Entity
public class Person {
    @Id @GeneratedValue
    private long id;
    private String userid;
    private String firstname;
    private String lastname;
    private String password;
    private Vector<HasActivity> myact;

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
}
