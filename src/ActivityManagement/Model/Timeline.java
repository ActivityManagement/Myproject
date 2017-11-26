package ActivityManagement.Model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;


@Entity
public class Timeline {
    @Id @GeneratedValue
    private long id;
    private String date;
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private ArrayList<TimeItem> item = new ArrayList<TimeItem>();


    public Timeline(LocalDate date)
    {
        this.date = date.toString();
    }
    public String getDate() {
        return date;
    }

    public ArrayList<TimeItem> getItem() {
        return item;
    }

    public void addTime(TimeItem item)
    {
        this.item.add(item);
    }
}
