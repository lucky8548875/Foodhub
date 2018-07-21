package com.hilagangluzon.foodhub;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SalesInfoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> keys;
    private HashMap<String, Product> map;

    public SalesInfoAdapter(Context context, HashMap<String, Product> map)
    {
        this.context = context;
        this.map = map;
        this.keys = new ArrayList<>();
        this.keys.addAll(map.keySet());
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int i) {
        return map.get(keys.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String getItemTag(int i) {
        return keys.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String key = keys.get(i);
        Product value = (Product) getItem(i);

        if(view == null)
        {
            LayoutInflater li = LayoutInflater.from(this.context);
            view = li.inflate(R.layout.sales_info, null);
        }

        TextView txfName, txfPrice, txfQty, txfSubt;

        txfName = view.findViewById(R.id.txfName);
        txfPrice = view.findViewById(R.id.txfPrice);
        txfQty = view.findViewById(R.id.txfQty);
        txfSubt = view.findViewById(R.id.txfSubt);

        txfName.setText(value.getName());
        txfPrice.setText(String.format("%.2f", value.getPrice()));
        txfQty.setText(String.valueOf(value.getUnits_sold()));
        txfSubt.setText(String.format("%.2f", value.pickSubtotal()));

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
