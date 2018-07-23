package com.hilagangluzon.foodhub.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hilagangluzon.foodhub.Classes.Order;
import com.hilagangluzon.foodhub.Classes.OrderDetail;
import com.hilagangluzon.foodhub.Customer.Dashboard;
import com.hilagangluzon.foodhub.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore db;
    public static ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    double totalCost = 0;

    ListView lstCart;
    ArrayAdapter adpCart;

    TextView lblTotal;

    EditText txfCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();

        lstCart = findViewById(R.id.lstCart);
        Log.d("size", orderDetails.size()+"");
        if(!orderDetails.isEmpty()) {
            adpCart = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderDetails);
            lstCart.setAdapter(adpCart);
        }

        lblTotal = findViewById(R.id.lblTotal);
        if(orderDetails.isEmpty())
        {
            lblTotal.setText("Your cart is empty!");
        }
        else
        {
            for(OrderDetail od : orderDetails)
            {
                totalCost += od.pickSubtotal();
            }
            lblTotal.setText(String.format("P%.2f", totalCost));
        }

        txfCash = findViewById(R.id.txfCash);
    }

    public void checkout(View v)
    {
        /*try
        {*/
            double cash = Double.valueOf(txfCash.getText().toString());

            if(!orderDetails.isEmpty())
            {
                if(cash >= totalCost)
                {
                    String newOrderId = db.collection("orders").document().getId();
                    Date dateNow = Calendar.getInstance().getTime();

                    Order order = new Order();
                    order.setUser_id(Dashboard.loggedInUserId);
                    order.setUsername(Dashboard.loggedInUserInfo.getUsername());
                    order.setOrder_date(dateNow);
                    order.setStatus("Complete");

                    db.collection("orders").document(newOrderId).set(order);

                    final CollectionReference prodref = db.collection("products");
                    final CollectionReference odref = db.collection("order_details");
                    for(OrderDetail od : orderDetails)
                    {
                        od.setOrder_id(newOrderId);
                        od.setOrder_date(Calendar.getInstance().getTime());
                        odref.add(od);

                        final OrderDetail odNow = od;
                        prodref.document(od.getProduct_id()).get().addOnSuccessListener(
                                new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        int in_stock = documentSnapshot.get("in_stock") != null ? Integer.valueOf(documentSnapshot.get("in_stock").toString()) : 0;
                                        prodref.document(odNow.getProduct_id()).update("in_stock", in_stock - odNow.getQuantity());

                                        int units_sold = documentSnapshot.get("units_sold") != null ? Integer.valueOf(documentSnapshot.get("units_sold").toString()) : 0;
                                        prodref.document(odNow.getProduct_id()).update("units_sold", units_sold + odNow.getQuantity());
                                    }
                                }
                        );

                    }

                    finish();
                    orderDetails.clear();
                    Toast.makeText(this, "Order successful!\nYour change is " + String.format("P%.2f", cash-totalCost), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Insufficient amount of cash!", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Your cart is empty!", Toast.LENGTH_LONG).show();
            }
        /*}
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
        }*/
    }
}
