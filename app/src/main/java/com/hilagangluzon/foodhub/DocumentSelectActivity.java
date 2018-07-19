package com.hilagangluzon.foodhub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

public class DocumentSelectActivity extends AppCompatActivity implements OnCompleteListener<QuerySnapshot>
        /*com.google.firebase.firestore.EventListener<QuerySnapshot>*/, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
{

    FirebaseFirestore db;
    FirestoreTools fs;

    Bundle fromPrev;
    String collection;
    Class c;

    TextView lblColls;

    Spinner spnCateg;
    String[] categories;
    ArrayAdapter adpCateg;

    ListView lstDocs;
    HashMapAdapter adpDocs;
    //SimpleAdapter adpDocs;
    //ArrayAdapter<Object> adpDocs;

    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_select);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        fromPrev = getIntent().getExtras();
        collection = fromPrev.getString("collection");
        //Toast.makeText(this, collection, Toast.LENGTH_LONG).show();
        switch(collection)
        {
            case User.COLLECTION_NAME: c = User.class; break;
            case Product.COLLECTION_NAME: c = Product.class; break;
            case Order.COLLECTION_NAME: c = Order.class ; break;
            //case Message.COLLECTION_NAME: c = Message.class ; break;
            default: c = Object.class; return;
        }

        /*User u = new User();

        u.setUsername("fakehappy");
        fs.insert("users", u);*/
        /*HashMap<String, Object> gotit = new HashMap<>();
        gotit.put("username", "reallysad");
        fs.update("users", "AcjxtUhOCiLDZGW250gc", gotit);*/
        //fs.delete("users", "AcjxtUhOCiLDZGW250gc");
        /*db.document("/users/OTlZPpoU09JqIx0Q0yDH").get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.d(task.getResult().getId(), task.getResult().get("username").toString() + " " + task.getResult().get("ref").toString());
                        db.document(((DocumentReference) task.getResult().get("ref")).getPath()).get().addOnCompleteListener(
                                new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Log.d(task.getResult().getId(), task.getResult().get("name").toString());
                                    }
                                }
                        );
                    }
                }
        );*/

        lblColls = findViewById(R.id.lblColls);
        lblColls.setText(collection.toUpperCase());

        spnCateg = findViewById(R.id.spnCateg);

        if(collection.equals("products"))
        {

            categories = new String[]
                    {
                            "All", "Frappe", "Hot Coffee", "Cold Drink", "Sandwich", "Bottled Drink", "Etc"
                    };
            adpCateg = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories);
            spnCateg.setAdapter(adpCateg);
            spnCateg.setOnItemSelectedListener(this);
        }
        else
        {
            spnCateg.setVisibility(View.GONE);
        }

        lstDocs = findViewById(R.id.lstDocs);
        adpDocs = new HashMapAdapter(this, android.R.layout.simple_list_item_1);
        lstDocs.setAdapter(adpDocs);
        lstDocs.setOnItemClickListener(this);

        fabAdd = findViewById(R.id.fabAdd);

        if(collection.equals("orders"))
        {
            fabAdd.setVisibility(View.GONE);
        }

        fs.selectAll(collection).addOnCompleteListener(this);
        //fs.referToCollection(collection).addSnapshotListener(this);
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

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task)
    {

        for(DocumentSnapshot document: task.getResult())
        {
            //Log.d("find me", document.getId()+":"+document.toObject(c));
            adpDocs.add(document.getId(), document.toObject(c));
        }

        //adpDocs = new HashMapAdapter(this, android.R.layout.simple_list_item_1, records);
        //lstDocs.setAdapter(adpDocs);

        //debug
        //Toast.makeText(this, "Something happened!", Toast.LENGTH_LONG).show();

        adpDocs.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent toSomewhere;
        Bundle stuff = new Bundle();
        switch(collection)
        {
            case User.COLLECTION_NAME:
                toSomewhere = new Intent(this, AdminUserView.class);
                stuff.putString("collection", collection);
                stuff.putString("id", view.findViewById(android.R.id.text1).getTag()+"");
                break;
            case Product.COLLECTION_NAME:
                toSomewhere = new Intent(this, AdminProductView.class);
                stuff.putString("collection", collection);
                stuff.putString("id", view.findViewById(android.R.id.text1).getTag()+"");
                break;
            case Order.COLLECTION_NAME:
                toSomewhere = new Intent(this, AdminOrderView.class);
                stuff.putString("collection", collection);
                stuff.putString("id", view.findViewById(android.R.id.text1).getTag()+"");
                //stuff.putString("username",((Order) adpDocs.getItem(i)).getUsername()); //EXP
                //Log.d("username", ((Order) adpDocs.getItem(i)).getUsername());
                break;
            //case Message.COLLECTION_NAME: toAdminMessageView(); break;
            default: return;
        }
        toSomewhere.putExtras(stuff);
        startActivity(toSomewhere);
        finish();
    }

    /*@Override
    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {

        //HashMap<String, Object> records = new HashMap<>();
        for (DocumentChange dc : snapshots.getDocumentChanges()) {
            switch (dc.getType()) {
                *//*case ADDED:
                    adpDocs.add(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    //records.put(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    break;*//*
                *//*case MODIFIED:
                    adpDocs.add(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    //records.put(dc.getDocument().getId(), dc.getDocument().toObject(c));
                    break;*//*
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
    }*/

    public void add(View v)
    {
        Intent toSomewhere;
        Bundle stuff = new Bundle();
        switch(collection)
        {
            case User.COLLECTION_NAME:
                toSomewhere = new Intent(this, AdminUserView.class);
                stuff.putString("collection", collection);
                break;
            case Product.COLLECTION_NAME:
                toSomewhere = new Intent(this, AdminProductView.class);
                stuff.putString("collection", collection);
                break;
            //case Order.COLLECTION_NAME: toAdminOrderView(); break;
            //case Message.COLLECTION_NAME: toAdminMessageView(); break;
            default: return;
        }
        toSomewhere.putExtras(stuff);
        startActivity(toSomewhere);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adpDocs.clear();
        if(i == 0)
        {
            fs.selectAll(collection).addOnCompleteListener(this);
        }
        else
        {
            fs.select(collection, "category", "=", categories[i]).addOnCompleteListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
