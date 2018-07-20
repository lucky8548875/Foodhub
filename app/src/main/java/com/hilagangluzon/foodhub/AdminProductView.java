package com.hilagangluzon.foodhub;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AdminProductView extends AppCompatActivity implements OnSuccessListener<DocumentSnapshot> {

    FirebaseFirestore db;
    FirestoreTools fs;

    //Bundle fromPrev;
    //String collection;
    static String id;

    KeyListener edtKeyL;

    EditText txfName, txfDesc, txfPrice, txfInst;
    Button btnAdd, btnEdit, btnDelete;

    Spinner spnCateg;
    String[] categories;
    ArrayAdapter adpCateg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_view);

        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);

        //fromPrev = fromPrev != null ? getIntent().getExtras() : new Bundle();
        //collection = fromPrev.containsKey("collection") ? fromPrev.getString("collection") : "products";
        //id = fromPrev.containsKey("id") ? fromPrev.getString("id") : "nvm";
        //id = getIntent().getExtras() != null ? getIntent().getExtras().containsKey("id") ? getIntent().getExtras().getString("id") : "nvm" : "nvm";

        txfName = findViewById(R.id.txfName);
        txfDesc = findViewById(R.id.txfDesc);
        txfPrice = findViewById(R.id.txfPrice);
        txfInst = findViewById(R.id.txfInst);
        
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        spnCateg = findViewById(R.id.spnCateg);
        categories = new String[]
                {
                        "Frappe", "Hot Coffee", "Cold Drink", "Sandwich", "Bottled Drink", "Etc"
                };
        adpCateg = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories);
        spnCateg.setAdapter(adpCateg);

        if(id == null)
        {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        else
        {
            edtKeyL = txfName.getKeyListener();

            txfName.setKeyListener(null);
            txfDesc.setKeyListener(null);
            txfPrice.setKeyListener(null);
            txfInst.setKeyListener(null);

            btnAdd.setVisibility(View.GONE);

            spnCateg.setEnabled(false);
            spnCateg.setClickable(false);

            fs.select(DocumentSelectActivity.collection, id).addOnSuccessListener(this);
        }
    }

    public void add(View v)
    {
        Product p = new Product();
        p.setName(txfName.getText().toString());
        p.setDescription(txfDesc.getText().toString());
        p.setPrice(Double.valueOf(txfPrice.getText().toString()));
        p.setIn_stock(Integer.valueOf(txfInst.getText().toString()));
        p.setCategory(spnCateg.getSelectedItem().toString());
        fs.insert(DocumentSelectActivity.collection, p);

        txfName.setText("");
        txfDesc.setText("");
        txfPrice.setText("");
        txfInst.setText("");
    }
    
    public void editOrSave(View v)
    {
        String state = btnEdit.getText().toString();
        if(state.equals("Edit"))
        {
            txfName.setKeyListener(edtKeyL);
            txfDesc.setKeyListener(edtKeyL);
            txfPrice.setKeyListener(edtKeyL);
            txfInst.setKeyListener(edtKeyL);

            spnCateg.setEnabled(true);
            spnCateg.setClickable(true);

            btnEdit.setText("Save");
        }
        else if (state.equals("Save"))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Save changes?")
                    .setMessage("Are you sure you want to save changes made to this product?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            /*HashMap<String, Object> values = new HashMap<>();
                            values.put("name")
                            fs.update(collection, id, values);*/
                            Product p = new Product();
                            p.setName(txfName.getText().toString());
                            p.setDescription(txfDesc.getText().toString());
                            p.setPrice(Double.valueOf(txfPrice.getText().toString()));
                            p.setIn_stock(Integer.valueOf(txfInst.getText().toString()));
                            p.setCategory(spnCateg.getSelectedItem().toString());
                            fs.update(DocumentSelectActivity.collection, id, p);

                            txfName.setKeyListener(null);
                            txfDesc.setKeyListener(null);
                            txfPrice.setKeyListener(null);
                            txfInst.setKeyListener(null);

                            btnEdit.setText("Edit");

                            spnCateg.setEnabled(false);
                            spnCateg.setClickable(false);
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            //resume timer
                        }
                    })
                    .show();
        }

    }

    public void delete(View v)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete this product?")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        fs.delete(DocumentSelectActivity.collection, id);
                        goBack();
                        //finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //resume timer
                    }
                })
                .show();
    }
    
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        txfName.setText(documentSnapshot.get("name").toString());
        txfDesc.setText(String.valueOf(documentSnapshot.get("description")));
        txfPrice.setText(String.valueOf(documentSnapshot.get("price")));
        txfInst.setText(String.valueOf(documentSnapshot.get("in_stock")));
        spnCateg.setSelection(adpCateg.getPosition(documentSnapshot.get("category")));
    }

    @Override
    public void onBackPressed()
    {
        goBack();
    }

    private void goBack()
    {
        Intent toReturn = new Intent(this, DocumentSelectActivity.class);
        //toReturn.putExtra("collection", Product.COLLECTION_NAME);
        id = null;
        startActivity(toReturn);
        finish();
    }
}
