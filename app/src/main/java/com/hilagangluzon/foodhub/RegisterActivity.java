package com.hilagangluzon.foodhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirestoreTools fs;

    EditText txfUser, txfPass, txfFname, txfMname, txfLname, txfEmail;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        txfUser = findViewById(R.id.txfUser);
        txfPass = findViewById(R.id.txfPass);
        txfFname = findViewById(R.id.txfFname);
        txfMname = findViewById(R.id.txfMname);
        txfLname = findViewById(R.id.txfLname);
        txfEmail = findViewById(R.id.txfEmail);

        btnReg = findViewById(R.id.btnReg);
    }

    public void register(View v)
    {
        String username = String.valueOf(txfUser.getText());
        String password = txfPass.getText().toString();
        String fname = txfFname.getText().toString();
        String mname = txfMname.getText().toString();
        String lname = txfLname.getText().toString();
        String email = txfEmail.getText().toString();
        if (!username.equals("") && !password.equals("") && !fname.equals("") && !mname.equals("") && !lname.equals("") && !email.equals(""))
        {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setFirst_name(fname);
            u.setMiddle_name(mname);
            u.setLast_name(lname);
            u.setEmail(email);
            u.setIs_admin(false);
            fs.insert("users", u);

            finish();
        }
        else
        {
            Toast.makeText(this, "Missing fields detected.", Toast.LENGTH_LONG).show();
        }
    }
}
