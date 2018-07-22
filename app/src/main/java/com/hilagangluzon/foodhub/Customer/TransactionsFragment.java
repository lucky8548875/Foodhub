package com.hilagangluzon.foodhub.Customer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hilagangluzon.foodhub.Adapters.HashMapAdapter;
import com.hilagangluzon.foodhub.Classes.Order;
import com.hilagangluzon.foodhub.R;
import com.hilagangluzon.foodhub.UserOrderView;

import java.util.HashMap;

public class TransactionsFragment extends Fragment implements AdapterView.OnItemClickListener
{
    FirebaseFirestore db;

    ListView lstTrans;
    HashMap<String, Object> orders;
    HashMapAdapter adpTrans;

    public TransactionsFragment() {

    }

    @Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

        db = FirebaseFirestore.getInstance();

        lstTrans = getActivity().findViewById(R.id.lstTrans);
        orders = new HashMap<>();
        db.collection("orders").whereEqualTo("user_id", Dashboard.loggedInUserId).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for(DocumentSnapshot documentSnapshot : snapshots)
                        {
                            orders.put(documentSnapshot.getId(), documentSnapshot.toObject(Order.class));
                        }
                        adpTrans = new HashMapAdapter(getContext(), android.R.layout.simple_list_item_1, orders);
                        lstTrans.setAdapter(adpTrans);
                    }
                }
        );
        lstTrans.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent toDetails = new Intent(getContext(), UserOrderView.class);
        UserOrderView.orderId = view.getTag().toString();
        UserOrderView.order = (Order) orders.get(view.getTag().toString());
        startActivity(toDetails);
    }
}
