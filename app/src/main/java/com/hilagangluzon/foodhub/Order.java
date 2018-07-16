package com.hilagangluzon.foodhub;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Order extends Object
{
    private String id;
    private String user_id;
    private String order_date;
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

    public Order(String user_id, String order_date, String status)
    {
        this.setUserId(user_id);
        this.setOrderDate(order_date);
        this.setStatus(status);
    }

    public String getOrderDate() {
        return order_date;
    }

    public void setOrderDate(String order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return getOrderDate();
    }
}
