package in.ccode.explorerschool.calendar;

import java.util.Date;

public class Events {
    private Date date;
    private String event;
    public Events() {
    }

    public Events(Date date, String event) {
        this.date = date;
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
