package in.ccode.explorerschool.homeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import in.ccode.explorerschool.R;
import in.ccode.explorerschool.assignmentActivity.AssignmentActivity;
import in.ccode.explorerschool.attendanceActivity.AttendanceActivity;
import in.ccode.explorerschool.calendarActivity.CalendarActivity;
import in.ccode.explorerschool.circularActivity.CircularActivity;
import in.ccode.explorerschool.feesActivity.FeesActivity;
import in.ccode.explorerschool.loginActivity.LoginActivity;
import in.ccode.explorerschool.marksActivity.MarksActivity;

public class HomeActivity extends AppCompatActivity {

    TextView profileName, profileClass, profileRollNo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CardView attendanceMenu, assignmentMenu, calendarMenu, marksMenu, circularMenu, feesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_roll_no),MODE_PRIVATE);
        String sharedRollNo = preferences.getString("profile_roll_no", "");

        initializeId();
        updateProfileCard(sharedRollNo);
        gridViewClickListener();
    }

    private void initializeId() {
        profileName = (TextView) findViewById(R.id.profile_name);
        profileClass = (TextView) findViewById(R.id.profile_class);
        profileRollNo = (TextView) findViewById(R.id.profile_roll_no);

        attendanceMenu = (CardView) findViewById(R.id.attendance_menu);
        assignmentMenu = (CardView) findViewById(R.id.assignment_menu);
        calendarMenu = (CardView) findViewById(R.id.calendar_menu);
        marksMenu = (CardView) findViewById(R.id.marks_menu);
        circularMenu = (CardView) findViewById(R.id.circulars_menu);
        feesMenu = (CardView) findViewById(R.id.fees_menu);
    }

    private void updateProfileCard(String sharedRollNo) {
        db.collection("student")
                .whereEqualTo("roll_no", sharedRollNo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Login Activity", document.getId() + " => " + document.getData());
                                profileName.setText(String.format("%s %s", document.getString("first_name"), document.getString("last_name")));
                                profileClass.setText(String.format("%s %s", document.getString("class"), document.getString("section")));
                                profileRollNo.setText(document.getString("roll_no"));
                            }
                        } else {
                            Log.d("Login Activity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void gridViewClickListener() {
        //TODO add grid Layout click listener
        attendanceMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        assignmentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AssignmentActivity.class);
                startActivity(intent);
            }
        });

        calendarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        marksMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MarksActivity.class);
                startActivity(intent);
            }
        });

        circularMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CircularActivity.class);
                startActivity(intent);
            }
        });

        feesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeesActivity.class);
                startActivity(intent);
            }
        });
    }
}
