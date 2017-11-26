package ActivityManagement.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Timeline {
    private LocalDate date;
    private ArrayList<TimeItem> item = new ArrayList<TimeItem>();

    public LocalDate getDate() {
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
