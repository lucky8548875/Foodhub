package com.hilagangluzon.foodhub.Customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hilagangluzon.foodhub.Adapters.ProductMenuItemAdapter;
import com.hilagangluzon.foodhub.CartActivity;
import com.hilagangluzon.foodhub.Classes.Product;
import com.hilagangluzon.foodhub.Classes.User;
import com.hilagangluzon.foodhub.FirestoreTools;
import com.hilagangluzon.foodhub.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MenuFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    FirebaseFirestore db;
    ListView listView;
    HashMap<String, Product> products;
    ProductMenuItemAdapter adpList;

    Spinner spinner;
    String[] categories;
    ArrayAdapter adpSpin;

    FloatingActionButton fabCart;

    public MenuFragment(){


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        db = FirebaseFirestore.getInstance();

        //Log.d("products", products.get(0).toString());
        listView = getActivity().findViewById(R.id.listView);
        Log.d("dbinst", "Db instance!");
        products = new HashMap<>();
        //adpList = new ProductMenuItemAdapter(getContext(), R.layout.product_item_layout);
        db.collection("products").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("prog", "Inside onComplete");
                            for(DocumentSnapshot documentSnapshot: task.getResult()){
                                Log.d("what?", documentSnapshot.getId() + ":" + documentSnapshot.get("name"));
                                String id = documentSnapshot.getId();
                                Product product = documentSnapshot.toObject(Product.class);
                                products.put(id, product);
                            }
                            adpList = new ProductMenuItemAdapter(getContext(), R.layout.product_item_layout, products);
                            listView.setAdapter(adpList);
                        }
                        else
                        {
                            Log.d("Bye", "byeeeeee");
                            getActivity().finish();
                            return;
                        }
                    }
                }
        );
        listView.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        categories = new String[]
                {
                        "All", "Frappe", "Hot Coffee", "Cold Drink", "Sandwich", "Bottled Drink", "Etc"
                };
        adpSpin = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adpSpin);
        spinner.setOnItemSelectedListener(this);

        fabCart = view.findViewById(R.id.fabCart);
        fabCart.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("idis", view.getTag().toString());
        Intent toProduct = new Intent(getContext(), UserProductView.class);
        UserProductView.id = view.getTag().toString();
        startActivity(toProduct);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        Intent toCart = new Intent(getContext(), CartActivity.class);
        startActivity(toCart);
    }
}
