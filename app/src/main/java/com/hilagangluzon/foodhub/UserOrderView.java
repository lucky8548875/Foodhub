package com.hilagangluzon.foodhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hilagangluzon.foodhub.Adapters.HashMapAdapter;
import com.hilagangluzon.foodhub.Classes.Order;
import com.hilagangluzon.foodhub.Classes.OrderDetail;

import java.util.HashMap;

public class UserOrderView extends AppCompatActivity {

    FirebaseFirestore db;

    public static String orderId;
    public static Order order;

    TextView lblDate, lblTotal;

    ListView lstDetails;
    HashMap<String, Object> ods;
    HashMapAdapter adpDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_view);

        db = FirebaseFirestore.getInstance();

        lblDate = findViewById(R.id.lblDate);
        lblDate.setText(order.getOrder_date().toString());

        lblTotal = findViewById(R.id.lblTotal);

        lstDetails = findViewById(R.id.lstDetails);
        ods = new HashMap<>();
        db.collection("order_details").whereEqualTo("order_id", orderId).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        double totalCost = 0;
                        for(DocumentSnapshot documentSnapshot : snapshots)
                        {
                            OrderDetail myOd = documentSnapshot.toObject(OrderDetail.class);
                            totalCost += myOd.pickSubtotal();
                            ods.put(documentSnapshot.getId(), myOd);
                        }
                        adpDetails = new HashMapAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ods);
                        lstDetails.setAdapter(adpDetails);

                        lblTotal.setText(""+totalCost);
                    }
                }
        );
    }
}
