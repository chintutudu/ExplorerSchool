package in.ccode.explorerschool.loginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import in.ccode.explorerschool.R;
import in.ccode.explorerschool.homeActivity.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginId, loginPassword;
    Button loginButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeId();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openHomeActivity();
                //TODO commented for testing
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_roll_no), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile_roll_no", "S12A32");
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeId() {
        loginId = (EditText) findViewById(R.id.login_id);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
    }

    private void openHomeActivity() {
        if ((!(loginId.getText().toString().trim().isEmpty())) && (!(loginPassword.getText().toString().trim().isEmpty()))) {
            db.collection("student")
                    .whereEqualTo("roll_no", loginId.getText().toString().trim())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("Login Activity", document.getId() + " => " + document.getData());
                                    if ((loginId.getText().toString().trim().equals(document.getString("roll_no")))
                                            && (loginPassword.getText().toString().trim().equals(document.getString("password")))) {
                                        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_roll_no), MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("profile_roll_no", document.getString("roll_no"));
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else {
                                Log.d("Login Activity", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Enter User ID and Password", Toast.LENGTH_SHORT).show();
        }
    }
}
