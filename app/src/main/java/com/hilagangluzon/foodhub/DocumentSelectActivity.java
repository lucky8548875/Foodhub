package com.hilagangluzon.foodhub;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

public class DocumentSelectActivity extends AppCompatActivity implements /*OnCompleteListener<QuerySnapshot>*/
        com.google.firebase.firestore.EventListener<QuerySnapshot>, AdapterView.OnItemClickListener
{

    FirebaseFirestore db;
    FirestoreTools fs;

    Bundle fromPrev;
    String collection;

    SearchView srcDocs;

    TextView lblColls;

    ListView lstDocs;
    HashMapAdapter adpDocs;
    //SimpleAdapter adpDocs;
    //ArrayAdapter<Object> adpDocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_select);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        fromPrev = getIntent().getExtras();
        collection = fromPrev.getString("collection");
        Toast.makeText(this, collection, Toast.LENGTH_LONG).show();

        srcDocs = findViewById(R.id.srcDocs);

        lblColls = findViewById(R.id.lblColls);
        lblColls.setText(collection);

        lstDocs = findViewById(R.id.lstDocs);
        adpDocs = new HashMapAdapter(this, android.R.layout.simple_list_item_1);
        lstDocs.setAdapter(adpDocs);
        lstDocs.setOnItemClickListener(this);
        //experimental
        //fs.select(collection).addOnCompleteListener(this);
        fs.referToCollection(collection).addSnapshotListener(this);
    }

    /*@Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        //adpDocs.clear();

        Class c;
        switch(collection)
        {
            case User.COLLECTION_NAME: c = User.class; break;
            case Product.COLLECTION_NAME: c = Product.class; break;
            case Order.COLLECTION_NAME: c = Order.class; break;
            //case Message.COLLECTION_NAME: field = "content"; break;
            default: return;
        }

        ArrayList<String> ids = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        for(DocumentSnapshot document: task.getResult())
        {
            ids.add(document.getId());
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("text", document.toObject(c));
            data.add(hm);
        }
        adpDocs = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1});
        adpDocs.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof  TextView)
                {
                    view.setTag(ids.get());
                }
                return false;
            }
        });
        lstDocs.setAdapter(adpDocs);


        //debug
        Toast.makeText(this, "Something happened!", Toast.LENGTH_LONG).show();

        adpDocs.notifyDataSetChanged();
    }*/

    /*@Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Class c;
        switch(collection)
        {
            case User.COLLECTION_NAME: c = User.class ; break;
            case Product.COLLECTION_NAME: c = Product.class ; break;
            case Order.COLLECTION_NAME: c = Order.class ; break;
            //case Message.COLLECTION_NAME: c = Message.class ; break;
            default: return;
        }

        HashMap<String, Object> records = new HashMap<>();
        for(DocumentSnapshot document: task.getResult())
        {
            records.put(document.getId(), document.toObject(c));
        }

        adpDocs = new HashMapAdapter(this, android.R.layout.simple_list_item_1, records);
        lstDocs.setAdapter(adpDocs);

        //debug
        //Toast.makeText(this, "Something happened!", Toast.LENGTH_LONG).show();

        adpDocs.notifyDataSetChanged();
    }*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, view.findViewById(android.R.id.text1).getTag()+"", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, adpDocs.getItemTag(i)+"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        Class c;
        switch(collection)
        {
            case User.COLLECTION_NAME: c = User.class ; break;
            case Product.COLLECTION_NAME: c = Product.class ; break;
            case Order.COLLECTION_NAME: c = Order.class ; break;
            //case Message.COLLECTION_NAME: c = Message.class ; break;
            default: return;
        }

        //HashMap<String, Object> records = new HashMap<>();
        for (DocumentChange dc : snapshots.getDocumentChanges())
        {
            switch(dc.getType()) {
                case ADDED:
                    adpDocs.add(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    //records.put(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    break;
                case MODIFIED:
                    adpDocs.add(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    //records.put(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    break;
                case REMOVED:
                    adpDocs.remove(dc.getDocument().getId());
                    //records.remove(dc.getDocument().getId());
                    break;
            }
        }

        //adpDocs = new HashMapAdapter(this, android.R.layout.simple_list_item_1, records);
        //lstDocs.setAdapter(adpDocs);

        //debug
        //Toast.makeText(this, "Something happened!", Toast.LENGTH_LONG).show();

        adpDocs.notifyDataSetChanged();
    }
}
