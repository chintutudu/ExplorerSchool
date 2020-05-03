package in.ccode.explorerschool.attendanceActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.ccode.explorerschool.R;
import in.ccode.explorerschool.calendar.Absent;
import in.ccode.explorerschool.calendar.CustomCalendar;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = "AttendanceActivity";
    LinearLayout layoutCalendar;
    View customView;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
    SimpleDateFormat simpleDateFormatDays = new SimpleDateFormat("dd");
    Date date = new Date();
    TextView month, attendanceDaysElapsed, attendancePresent, attendanceAbsent,attendanceMonthlyPercentage;
    SimpleDateFormat absentDate = new SimpleDateFormat("dd-MM-yyyy");
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<Absent> mAbsent;
    Absent absent;

    int presentCount, absentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        setInitializations();
        getData();
        setCardView();
        setCalenderView();
    }

    private void setInitializations() {
        customView = (View) findViewById(R.id.custom_view_attendance);
        layoutCalendar = (LinearLayout) findViewById(R.id.layoutCalender_attendance);
        month = (TextView) findViewById(R.id.attendance_month);
        attendanceDaysElapsed = (TextView) findViewById(R.id.attendance_days_elapsed);
        attendancePresent = (TextView) findViewById(R.id.attendance_present);
        attendanceAbsent = (TextView) findViewById(R.id.attendance_absent);
        attendanceMonthlyPercentage = (TextView) findViewById(R.id.attendance_monthly_percentage);
    }

    private void getData() {
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_roll_no),MODE_PRIVATE);
        String sharedRollNo = preferences.getString("profile_roll_no", "");

        db.collection("attendance")
                .whereEqualTo("roll_no", sharedRollNo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                absent = new Absent();
                                try {
                                    absent.setDate(absentDate.parse(document.getString("date")));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                absent.setRoll_no(document.getString("roll_no"));
                                absent.setTeacher_id(document.getString("teacher_id"));
                                //TODO check absent one
                                //mAbsent.add(absent);
                                absentCount++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setCardView() {
        int daysElapsed, present, absent, monthlyPercentage;
        month.setText(simpleDateFormat.format(date));
        attendanceDaysElapsed.setText(simpleDateFormatDays.format(date));

        daysElapsed = Integer.parseInt(simpleDateFormatDays.format(date));
        present = daysElapsed;
        monthlyPercentage = (daysElapsed/present) * 100;

        attendancePresent.setText(String.valueOf(present));
        attendanceAbsent.setText(String.valueOf(absentCount));
        attendanceMonthlyPercentage.setText(String.format("%s %%", String.valueOf(monthlyPercentage)));
    }

    private void setCalenderView() {
        ViewGroup parent = (ViewGroup) customView.getParent();
        parent.removeView(customView);
        layoutCalendar.removeAllViews();
        layoutCalendar.setOrientation(LinearLayout.VERTICAL);

        final CustomCalendar calendarCustomView = new CustomCalendar(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalendar.addView(calendarCustomView);
    }
}
