package com.hilagangluzon.foodhub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hilagangluzon.foodhub.Classes.Product;
import com.hilagangluzon.foodhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductMenuItemAdapter extends BaseAdapter {

    Context context;
    int resource;
    ArrayList<String> keys;
    HashMap<String, Product> productList;

    public ProductMenuItemAdapter(Context context, int resource)
    {
        this.context = context;
        this.keys = new ArrayList<>();
        this.productList = new HashMap<>();
        this.resource = resource;
    }

    public ProductMenuItemAdapter(Context context, int resource, HashMap<String, Product> productList){

        //super(context,resource,productList);
        this.context = context;
        this.productList = productList;
        this.keys = new ArrayList<>();
        this.keys.addAll(productList.keySet());
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(keys.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource,null, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        TextView priceView = (TextView) convertView.findViewById(R.id.price);

        Product product = (Product) getItem(position);
        titleView.setText(product.getName());
        priceView.setText(String.format("%.2f", product.getPrice()));
        Log.d("Hoy", "Gumana o");
        Log.d("Yo!", "It did run! " + product.getName() + " " + product.getPrice());

        convertView.setTag(keys.get(position));
        return convertView;

    }



}
