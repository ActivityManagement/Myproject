package ActivityManagement.Model;

import javax.persistence.*;

@Entity
public class Department {

    @Id @GeneratedValue
    //private Department department;
    private int id;
    private String DeptName;
    private long DeptMaster;
    private String DeptMasterName;



    public Department() {}

    public Department(String Deptname,String Deptmastername) {
        this.DeptName = Deptname;
        this.DeptMasterName = Deptmastername;
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


    public String getDeptMasterName() {
        return DeptMasterName;
    }

    public Department getDepartment() {
        return this;
    }
}
