package ActivityManagement.Controller;

import ActivityManagement.MainProgram;
import ActivityManagement.Model.ObjectDB;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id @GeneratedValue
    //private Department department;
    private int id;
    private String DeptName;
    private long DeptMaster;
    private int Member;



    public Department() {}

    public Department(String Deptname,long Deptmaster,int member) {
        this.DeptName = Deptname;
        this.DeptMaster = Deptmaster;
        this.Member = member;
    }

    public int getId() {
        return id;
    }


    public String getDeptName() {
        return DeptName;
    }

    public long getDeptMaster() {
        return DeptMaster;
    }

    public int getMember() {
        return Member;
    }

    public Department getDepartment() {
        return this;
    }
}
