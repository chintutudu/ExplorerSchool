package in.ccode.explorerschool.circularActivity;

public class CircularListData {
    private String date;
    private String details;
    private String heading;

    public CircularListData() {
    }

    public CircularListData(String date, String details, String heading) {
        this.date = date;
        this.details = details;
        this.heading = heading;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
