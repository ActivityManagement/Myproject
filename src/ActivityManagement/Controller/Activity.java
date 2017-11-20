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
    private ArrayList<Department> dept = new ArrayList<>();
    @OneToMany(fetch=FetchType.EAGER)
    private ArrayList<Person> actmember = new ArrayList<>();


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

    public String getPassword()
    {
        return password;
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
        return "Invalid";
    }

    public void addMember(Person p)
    {
        actmember.add(p);
    }

    public ArrayList<Person> getActmember() {
        return actmember;
    }

    public ArrayList<Person> getJoinedMember()
    {
        ArrayList<Person> p = new ArrayList<>();
        for (int i = 0; i < actmember.size(); i++) {
            Person person = actmember.get(i);
            for (int j = 0; j < person.getMyact().size(); j++) {
                HasActivity actperson = person.getMyact().get(j);
                if (actperson.getActivity().getActid().equals(actid)) // check person has act
                {
                    if (actperson.getApprove()==1) // check if joined
                    {
                        p.add(person);
                    }
                }

            }
        }
        return p;
    }

    public ArrayList<Person> getRequestMember()
    {
        ArrayList<Person> p = new ArrayList<>();
        for (int i = 0; i < actmember.size(); i++) {
            Person person = actmember.get(i);
            for (int j = 0; j < person.getMyact().size(); j++) {
                HasActivity actperson = person.getMyact().get(j);
                if (actperson.getActivity().getActid().equals(actid)) // check person has act
                {
                    if (actperson.getApprove()==0) // check if request
                    {
                        p.add(person);
                    }
                }

            }
        }
        return p;
    }

    public void addDept(Department d) { dept.add(d); }

    public ArrayList<Department> getMyActivityDept() {
        return dept;
    }
}
