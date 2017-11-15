package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id
    private int id;
    private String DeptName;
    private String DeptMaster;
    private int Member;

    public Department() {}

    public Department(String Deptname,String Deptmaster,int member) {
        this.DeptName = Deptname;
        this.DeptMaster = Deptmaster;
        this.Member = member;
    }

    public String getDeptName() {
        return DeptName;
    }

    public String getDeptMaster() {
        return DeptMaster;
    }

    public int getMember() {
        return Member;
    }
}
