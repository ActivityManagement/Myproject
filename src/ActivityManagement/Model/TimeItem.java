package ActivityManagement.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TimeItem {
    @Id @GeneratedValue
    private long id;
    private String time;
    private String detail;

    public TimeItem(String time,String detail)
    {
        this.time = time;
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public String getDetail() {
        return detail;
    }
}
