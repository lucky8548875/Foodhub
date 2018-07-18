package com.hilagangluzon.foodhub;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUserView extends AppCompatActivity implements OnSuccessListener<DocumentSnapshot> {

    FirebaseFirestore db;
    FirestoreTools fs;

    Bundle fromPrev;
    String collection;
    String id;

    KeyListener edtKeyL;

    EditText txfUser, txfPass, txfFname, txfMname, txfLname, txfEmail;
    Button btnAdd, btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_view);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        fromPrev = getIntent().getExtras();
        collection = fromPrev.containsKey("collection") ? fromPrev.getString("collection") : "none";
        id = fromPrev.containsKey("id") ? fromPrev.getString("id") : "nvm";
        
        txfUser = findViewById(R.id.txfUser);
        txfPass = findViewById(R.id.txfPass);
        txfFname = findViewById(R.id.txfFname);
        txfMname = findViewById(R.id.txfMname);
        txfLname = findViewById(R.id.txfLname);
        txfEmail = findViewById(R.id.txfEmail);

        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        if(id.equals("nvm"))
        {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        else
        {
            edtKeyL = txfUser.getKeyListener();

            txfUser.setKeyListener(null);
            txfPass.setKeyListener(null);
            txfFname.setKeyListener(null);
            txfMname.setKeyListener(null);
            txfLname.setKeyListener(null);
            txfEmail.setKeyListener(null);

            btnAdd.setVisibility(View.GONE);
            fs.select(collection, id).addOnSuccessListener(this);
        }
    }
    
    public void add(View v)
    {
        User u = new User();
        u.setUsername(String.valueOf(txfUser.getText()));
        u.setPassword(String.valueOf(txfPass.getText()));
        u.setFirst_name(String.valueOf(txfFname.getText()));
        u.setMiddle_name(String.valueOf(txfMname.getText()));
        u.setLast_name(String.valueOf(txfLname.getText()));
        u.setEmail(String.valueOf(txfEmail.getText()));
        fs.insert(collection, u);

        txfUser.setText("");
        txfPass.setText("");
        txfFname.setText("");
        txfMname.setText("");
        txfLname.setText("");
        txfEmail.setText("");
    }

    public void editOrSave(View v)
    {
        String state = btnEdit.getText().toString();
        if(state.equals("Edit"))
        {
            txfUser.setKeyListener(edtKeyL);
            txfPass.setKeyListener(edtKeyL);
            txfFname.setKeyListener(edtKeyL);
            txfMname.setKeyListener(edtKeyL);
            txfLname.setKeyListener(edtKeyL);
            txfEmail.setKeyListener(edtKeyL);

            btnEdit.setText("Save");
        }
        else if (state.equals("Save"))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Save changes?")
                    .setMessage("Are you sure you want to save changes made to this user?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            User u = new User();
                            u.setUsername(String.valueOf(txfUser.getText()));
                            u.setPassword(String.valueOf(txfPass.getText()));
                            u.setFirst_name(String.valueOf(txfFname.getText()));
                            u.setMiddle_name(String.valueOf(txfMname.getText()));
                            u.setLast_name(String.valueOf(txfLname.getText()));
                            u.setEmail(String.valueOf(txfEmail.getText()));
                            fs.update(collection, id, u);

                            txfUser.setKeyListener(null);
                            txfPass.setKeyListener(null);
                            txfFname.setKeyListener(null);
                            txfMname.setKeyListener(null);
                            txfLname.setKeyListener(null);
                            txfEmail.setKeyListener(null);

                            btnEdit.setText("Edit");
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            
                        }
                    })
                    .show();
        }

    }

    public void delete(View v)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete this user?")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        fs.delete(collection, id);
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //resume timer
                    }
                })
                .show();
        goBack();
    }

    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        txfUser.setText(String.valueOf(documentSnapshot.get("username")));
        txfPass.setText(String.valueOf(documentSnapshot.get("password")));
        txfFname.setText(String.valueOf(documentSnapshot.get("first_name")));
        txfMname.setText(String.valueOf(documentSnapshot.get("middle_name")));
        txfLname.setText(String.valueOf(documentSnapshot.get("last_name")));
        txfEmail.setText(String.valueOf(documentSnapshot.get("email")));
    }

    @Override
    public void onBackPressed()
    {
        goBack();
    }

    public void goBack()
    {
        Intent toReturn = new Intent(this, DocumentSelectActivity.class);
        toReturn.putExtra("collection", User.COLLECTION_NAME);
        startActivity(toReturn);
        finish();
    }
}
