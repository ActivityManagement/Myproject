package ActivityManagement.Model;

public class TimeItem {
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
