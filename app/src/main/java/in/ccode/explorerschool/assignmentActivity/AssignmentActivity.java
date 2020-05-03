package in.ccode.explorerschool.assignmentActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.ccode.explorerschool.R;

public class AssignmentActivity extends AppCompatActivity {

    TextView month;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
    Date date = new Date();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<AssignmentListData> myListData;
    private AssignmentListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        setInitializations();
        getData();
        setData();
    }

    private void setInitializations() {
        month = (TextView) findViewById(R.id.assignment_month);
        month.setText(simpleDateFormat.format(date));
        myListData = new ArrayList<>();
        listAdapter = new AssignmentListAdapter(myListData);
    }

    private void getData() {
        db.collection("assignment").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        AssignmentListData listData = doc.getDocument().toObject(AssignmentListData.class);
                        myListData.add(listData);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setData() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.assignment_recycler_view);
        AssignmentListAdapter adapter = new AssignmentListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
