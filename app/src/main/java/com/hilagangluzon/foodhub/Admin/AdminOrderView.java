package com.hilagangluzon.foodhub.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hilagangluzon.foodhub.FirestoreTools;
import com.hilagangluzon.foodhub.Adapters.HashMapAdapter;
import com.hilagangluzon.foodhub.Classes.OrderDetail;
import com.hilagangluzon.foodhub.R;

import java.text.SimpleDateFormat;

public class AdminOrderView extends AppCompatActivity {

    FirebaseFirestore db;
    FirestoreTools fs;

    //Bundle fromPrev;
    //String collection;
    static String id;

    TextView lblBy, lblOn, lblTotal;

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
        lblTotal = findViewById(R.id.lblTotal);

        lstDetails = findViewById(R.id.lstDetails);
        adpDetails = new HashMapAdapter(this, android.R.layout.simple_list_item_1);
        lstDetails.setAdapter(adpDetails);

        //Log.d("idis", id);
        fs.select(DocumentSelectActivity.collection, id).addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        SimpleDateFormat formattter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        lblBy.setText("Ordered by: @" + documentSnapshot.get("username").toString());
                        lblOn.setText("Ordered on: " + formattter.format(documentSnapshot.getDate("order_date")));
                    }
                }
        );

        fs.selectWhere("order_details", "order_id", "=", id).addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        double total = 0;
                        for(DocumentSnapshot document : snapshots)
                        {
                            //Log.d("tester", document.getId() + " " + document.toObject(OrderDetail.class).toString());
                            adpDetails.add(document.getId(), document.toObject(OrderDetail.class));
                            total += document.getDouble("price")*Integer.valueOf(document.get("quantity").toString());
                        }

                        adpDetails.notifyDataSetChanged();
                        lblTotal.setText("Total: " + String.format("%.2f", total));
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
