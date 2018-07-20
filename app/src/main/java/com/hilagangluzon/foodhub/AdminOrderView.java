package com.hilagangluzon.foodhub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminOrderView extends AppCompatActivity {

    FirebaseFirestore db;
    FirestoreTools fs;

    //Bundle fromPrev;
    //String collection;
    static String id;

    TextView lblBy, lblOn;

    ListView lstDetails;
    HashMapAdapter adpDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_view);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        //fromPrev = fromPrev != null ? getIntent().getExtras() : new Bundle();
        //collection = fromPrev.containsKey("collection") ? fromPrev.getString("collection") : "orders";
        //id = getIntent().getExtras().containsKey("id") ? getIntent().getExtras().getString("id") : "nvm";

        //Log.d("bundle", fromPrev.toString());
        Log.d("id", id);

        lblBy = findViewById(R.id.lblBy);
        lblOn = findViewById(R.id.lblOn);

        lstDetails = findViewById(R.id.lstDetails);
        adpDetails = new HashMapAdapter(this, android.R.layout.simple_list_item_1);
        lstDetails.setAdapter(adpDetails);

        //Log.d("idis", id);
        fs.select(DocumentSelectActivity.collection, id).addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        lblBy.setText("Ordered by: @" + documentSnapshot.get("username").toString());
                        lblOn.setText("Ordered on: " + documentSnapshot.get("order_date").toString());
                    }
                }
        );

        fs.select("order_details", "order_id", "=", id).addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for(DocumentSnapshot document : snapshots)
                        {
                            Log.d("tester", document.getId() + " " + document.toObject(OrderDetail.class).toString());
                            adpDetails.add(document.getId(), document.toObject(OrderDetail.class));
                        }

                        adpDetails.notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public void onBackPressed()
    {
        goBack();
    }

    private void goBack()
    {
        Intent toReturn = new Intent(this, DocumentSelectActivity.class);
        //toReturn.putExtra("collection", Order.COLLECTION_NAME);
        id = null;
        startActivity(toReturn);
        finish();
    }
}
