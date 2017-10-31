package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;

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

    public void joinAct(Activity a)
    {
        HasActivity ha = new HasActivity(a,0);
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(MainProgram.DBName);
        odb.saveObject(ha);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+MainProgram.personCurrent.getId()+"", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
            MainProgram.personCurrent = p;
            MainProgram.personCurrent.addAct(ha);
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }

    public String getFirstname()
    {
        return firstname;
    }
    public String getLastname()
    {
        return lastname;
    }
    public String getUserid()
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
}
