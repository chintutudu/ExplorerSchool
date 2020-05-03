package in.ccode.explorerschool.assignmentActivity;

public class AssignmentListData {
    private String subject_id;
    private String details;
    private String due_date;

    public AssignmentListData() {
    }

    public AssignmentListData(String subject_id, String details, String due_date) {
        this.subject_id = subject_id;
        this.details = details;
        this.due_date = due_date;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
