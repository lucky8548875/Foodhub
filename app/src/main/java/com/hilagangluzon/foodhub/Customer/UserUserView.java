package com.hilagangluzon.foodhub.Customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hilagangluzon.foodhub.Classes.User;
import com.hilagangluzon.foodhub.R;

import java.util.HashMap;

public class UserUserView extends AppCompatActivity {

    FirebaseFirestore db;

    EditText txfUser, txfPass, txfFname, txfMname, txfLname, txfEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_user_view);

        db = FirebaseFirestore.getInstance();

        txfUser = findViewById(R.id.txfUser);
        txfPass = findViewById(R.id.txfPass);
        txfFname = findViewById(R.id.txfFname);
        txfMname = findViewById(R.id.txfMname);
        txfLname = findViewById(R.id.txfLname);
        txfEmail = findViewById(R.id.txfEmail);

        User u = Dashboard.loggedInUserInfo;

        txfUser.setText(u.getUsername());
        txfPass.setText(u.getPassword());
        txfFname.setText(u.getFirst_name());
        txfMname.setText(u.getMiddle_name());
        txfLname.setText(u.getLast_name());
        txfEmail.setText(u.getEmail());
    }

    public void save(View v)
    {
        String username = String.valueOf(txfUser.getText());
        String password = txfPass.getText().toString();
        String fname = txfFname.getText().toString();
        String mname = txfMname.getText().toString();
        String lname = txfLname.getText().toString();
        String email = txfEmail.getText().toString();
        if (!username.equals("") && !password.equals("") && !fname.equals("") && !mname.equals("") && !lname.equals("") && !email.equals(""))
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            map.put("first_name", fname);
            map.put("middle_name", mname);
            map.put("last_name", lname);
            map.put("email", email);
            db.collection("users").document(Dashboard.loggedInUserId).update(map);

            User me = new User();
            me.setUsername(username);
            me.setPassword(password);
            me.setFirst_name(fname);
            me.setMiddle_name(mname);
            me.setLast_name(lname);
            me.setEmail(email);
            me.setIs_admin(false);
            Dashboard.loggedInUserInfo = me;
            Log.d("UserUUserUserView", me.toString());
            finish();
        }
        else
        {
            Toast.makeText(this, "Missing fields detected.", Toast.LENGTH_LONG).show();
        }
    }
}
