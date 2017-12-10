package ActivityManagement.Controller;

import ActivityManagement.Model.Person;

public class MemberJoinedTable {

    private String userid;
    private String name;
    private String role;
    private Person person;

    public MemberJoinedTable(String uid,String name,String role,Person p)
    {
        this.userid = uid;
        this.name = name;
        this.role = role;
        this.person = p;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
    public Person getPerson()
    {
        return person;
    }
}
