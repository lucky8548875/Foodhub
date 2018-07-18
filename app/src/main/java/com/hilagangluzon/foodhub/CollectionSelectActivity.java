package com.hilagangluzon.foodhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CollectionSelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ListView lstColls;
    String[] collections;
    ArrayAdapter<String> adpColls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_select);

        lstColls = findViewById(R.id.lstColls);
        collections = new String[]
                {
                        User.COLLECTION_NAME.toUpperCase(),
                        Product.COLLECTION_NAME.toUpperCase()//,
                        //Order.COLLECTION_NAME.toUpperCase()//,
                        //Message.COLLECTION_NAME.toUpperCase()
                };
        adpColls = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collections);
        lstColls.setAdapter(adpColls);
        lstColls.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent toNext = new Intent(this, DocumentSelectActivity.class);
        switch(position)
        {
            case 0: toNext.putExtra("collection", User.COLLECTION_NAME); break;
            case 1: toNext.putExtra("collection", Product.COLLECTION_NAME); break;
            //case 2: toNext.putExtra("collection", Order.COLLECTION_NAME); break;
            //case 3: toNext.putExtra("collection", Message.COLLECTION_NAME); break;
            default: break;
        }
        startActivity(toNext);
    }
}
