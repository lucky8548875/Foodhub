package com.hilagangluzon.foodhub;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Order extends Object
{
    private String id;
    private String user_id;
    private Date order_date;
    private String status;


    //public static final String FIELD_ = "";
    public static final String COLLECTION_NAME = "orders";
    public static final String FIELD_ID = "id";
    public static final String FIELD_CUSTOMER_ID = "user_id";
    public static final String FIELD_ORDER_DATE = "order_date";
    public static final String FIELD_STATUS = "status";

    public static final String[] FIELDS = new String[]
            {
                    FIELD_ID, FIELD_CUSTOMER_ID, FIELD_ORDER_DATE, FIELD_STATUS
            };

    public Order()
    {

    }

    public Order(String user_id, Date order_date, String status)
    {
        this.setUser_id(user_id);
        this.setOrder_date(order_date);
        this.setStatus(status);
    }

    public String acquireUsername()
    {
        String username = "";

        /*FirebaseFirestore.getInstance().collection("users").document(getUser_id()).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                       documentSnapshot.get("username");
                    }
                }
        );*/
        FirebaseFirestore.getInstance().collection("users").document(getUser_id()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        documentSnapshot.get("username");
                    }
                }
        );

        return username;
    }

    @Override
    public String toString()
    {
        return acquireUsername() + getOrder_date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
