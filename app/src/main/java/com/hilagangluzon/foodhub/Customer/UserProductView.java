package com.hilagangluzon.foodhub.Customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hilagangluzon.foodhub.Classes.OrderDetail;
import com.hilagangluzon.foodhub.Classes.Product;
import com.hilagangluzon.foodhub.R;

public class UserProductView extends AppCompatActivity {

    FirebaseFirestore db;
    static String id;
    Product product;

    TextView lblName, lblPrice, lblCateg, lblDesc;
    EditText txfQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_view);

        db = FirebaseFirestore.getInstance();

        lblName = findViewById(R.id.lblName);
        lblPrice = findViewById(R.id.lblPrice);
        lblCateg = findViewById(R.id.lblCateg);
        lblDesc = findViewById(R.id.lblDesc);

        txfQty = findViewById(R.id.txfQty);

        db.collection("products").document(id).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        product = documentSnapshot.toObject(Product.class);
                        lblName.setText(product.getName());
                        lblPrice.setText(String.format("P%.2f", product.getPrice()));
                        lblCateg.setText(product.getCategory());
                        lblDesc.setText(product.getDescription());
                    }
                }
        );
    }

    public void addToCart(View v)
    {
        int enteredQty = Integer.valueOf(txfQty.getText().toString());

        if (enteredQty != 0)
        {
            //check first if products is already in cart???
            if(enteredQty <= product.getIn_stock())
            {
                OrderDetail od = new OrderDetail();
                od.setName(product.getName());
                od.setPrice(product.getPrice());
                od.setQuantity(enteredQty);
                od.setProduct_id(id);

                int i;
                for(i = 0; i < CartActivity.orderDetails.size(); i++)
                {
                    if(od.hasSameProductAs(CartActivity.orderDetails.get(i)))
                    {
                        CartActivity.orderDetails.set(i, od);
                        break;
                    }
                }

                if(i == CartActivity.orderDetails.size())
                {
                    CartActivity.orderDetails.add(od);
                }
            }
            else
            {
                Toast.makeText(this, "Only " + product.getIn_stock() + " left in stock!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Don't order 0 of these please!", Toast.LENGTH_LONG).show();
        }
    }
}
