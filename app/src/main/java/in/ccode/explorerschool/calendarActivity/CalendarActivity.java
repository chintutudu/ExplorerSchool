package in.ccode.explorerschool.calendarActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.ccode.explorerschool.R;
import in.ccode.explorerschool.assignmentActivity.AssignmentListData;
import in.ccode.explorerschool.calendar.CustomCalendar;
import in.ccode.explorerschool.calendar.Events;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    LinearLayout layoutCalendar;
    View customView;
    TextView thisWeek;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
    SimpleDateFormat eventDate = new SimpleDateFormat("dd-MM-yyyy");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Events> mEvent;
    Events events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setInitializations();
        getData();
        setCardView();
        setCalenderView();
    }

    private void setInitializations() {
        customView = (View) findViewById(R.id.custom_view_calendar);
        layoutCalendar = (LinearLayout) findViewById(R.id.layoutCalender_calendar);
        thisWeek = (TextView) findViewById(R.id.calendar_this_week);
        mEvent = new ArrayList<>();
    }

    private void getData() {
        db.collection("calendar")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Calendar Activity", document.getId() + " => " + document.getString("date"));
                                events = new Events();
                                try {
                                    events.setDate(eventDate.parse(document.getString("date")));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                events.setEvent(document.getString("event"));
                                mEvent.add(events);
                            }
                        } else {
                            Log.d("Calendar Activity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setCardView() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String startDate = "", endDate = "";
        startDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);
        endDate = simpleDateFormat.format(calendar.getTime());
        thisWeek.setText(String.format("%s - %s", startDate, endDate));
    }

    private void setCalenderView() {
        ViewGroup parent = (ViewGroup) customView.getParent();
        parent.removeView(customView);
        layoutCalendar.removeAllViews();
        layoutCalendar.setOrientation(LinearLayout.VERTICAL);

        final CustomCalendar calendarCustomView = new CustomCalendar(this, mEvent);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalendar.addView(calendarCustomView);

        //TODO set click listener
        setDateClicked(calendarCustomView);
    }

    private void setDateClicked(CustomCalendar calendarCustomView) {
        calendarCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dateClicked = eventDate.format(parent.getAdapter().getItem(position));
                Toast.makeText(CalendarActivity.this, "" + dateClicked,Toast.LENGTH_SHORT).show();

                //TODO change logic
                db.collection("calendar")
                        .whereEqualTo("date", dateClicked)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
    }
}
