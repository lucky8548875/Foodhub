package com.hilagangluzon.foodhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity implements OnSuccessListener<QuerySnapshot>{

    FirebaseFirestore db;
    FirestoreTools fs;

    EditText txfUser, txfPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        txfUser = findViewById(R.id.txfUser);
        txfPass = findViewById(R.id.txfPass);
    }

    public void signIn(View v)
    {
        fs.select(User.COLLECTION_NAME, "username", "=", txfUser.getText().toString()).addOnSuccessListener(this);
    }

    public void goToSignUp(View v)
    {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
    }


    @Override
    public void onSuccess(QuerySnapshot snapshots) {
        String enteredUser = txfUser.getText().toString();
        String enteredPass = txfPass.getText().toString();

        for(DocumentSnapshot document : snapshots)
        {
            String queriedUser = document.get("username").toString();
            String queriedPass = document.get("password").toString();

            if(enteredUser.equals(queriedUser) && enteredPass.equals(queriedPass))
            {
                Intent toMain;
                Bundle stuff = new Bundle();
                if(document.getBoolean("is_admin"))
                {
                    toMain = new Intent(this, CollectionSelectActivity.class);
                    CollectionSelectActivity.loggedInUser = document.toObject(User.class);
                    //stuff.putString("logged_in_user", document.getId());
                    finish();
                }
                else
                {
                    toMain = new Intent(this, MainActivity.class); //this activity to should come from Main then to Dashboard
                    //Dashboard.loggedInUser = document.toObject(User.class);
                    //stuff.putString("logged_in_user", document.getId());
                    finish();
                }
                toMain.putExtras(stuff);
                startActivity(toMain);
                finish();
                return;
            }
        }
        Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show();
    }
}
