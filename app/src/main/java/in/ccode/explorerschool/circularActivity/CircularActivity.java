package in.ccode.explorerschool.circularActivity;

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

public class CircularActivity extends AppCompatActivity {
    TextView heading, details, dateCircular;
    SimpleDateFormat simpleDateFormatDays = new SimpleDateFormat("dd MMM yyyy");
    Date date = new Date();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<CircularListData> myListData;
    private CircularListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);

        setInitializations();
        getData();
        setData();
    }

    private void setInitializations() {
        heading = (TextView) findViewById(R.id.heading);
        details = (TextView) findViewById(R.id.detail);
        dateCircular = (TextView) findViewById(R.id.circular_date);
        dateCircular.setText(simpleDateFormatDays.format(date));
        myListData = new ArrayList<>();
        listAdapter = new CircularListAdapter(myListData);
    }

    private void getData() {
        db.collection("circular").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        CircularListData listData = doc.getDocument().toObject(CircularListData.class);
                        myListData.add(listData);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setData() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.circular_recycler_view);
        CircularListAdapter adapter = new CircularListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
