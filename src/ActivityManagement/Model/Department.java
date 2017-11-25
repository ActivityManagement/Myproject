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
    private int Member;
    private String Note;


    public Department() {}

    public Department(String Deptname,String Deptmastername,int member) {
        this.DeptName = Deptname;
        this.DeptMasterName = Deptmastername;
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

    public String getDeptMasterName() {
        return DeptMasterName;
    }

    public Department getDepartment() {
        return this;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

}
