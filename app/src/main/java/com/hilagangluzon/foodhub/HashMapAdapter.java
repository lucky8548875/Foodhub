package com.hilagangluzon.foodhub;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapAdapter extends BaseAdapter
{
    private Context context;
    private int resource;
    private HashMap<String, Object> map;
    private ArrayList<String> keys;

    public HashMapAdapter(Context context, int resource)
    {
        this.context = context;
        this.resource = resource;
        this.map = new HashMap<>();
        this.keys = new ArrayList<>();
    }

    public HashMapAdapter(Context context, int resource, HashMap<String, Object> map)
    {
        this.context = context;
        this.resource = resource;
        this.map = map;
        keys.addAll(map.keySet());
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
        String value = getItem(i).toString();

        if(view == null)
        {
            LayoutInflater li = LayoutInflater.from(this.context);
            view = li.inflate(this.resource, null);
        }

        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setTag(key);
        text1.setText(value);

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public void add(String key, Object value)
    {
        //this.keys.add(key);
        this.map.put(key, value);
        this.keys.clear();
        //this.keys = new ArrayList<>();
        this.keys.addAll(map.keySet());
    }

    public void set(String key, Object value)
    {
        if(map.containsKey(key))
        {
            //this.keys.add(key);
            this.map.put(key, value);
            this.keys.clear();
            //this.keys = new ArrayList<>();
            this.keys.addAll(map.keySet());
        }
    }

    public void remove(String key)
    {
        //this.keys.remove(key);
        this.map.remove(key);
        this.keys.clear();
        //this.keys = new ArrayList<>();
        this.keys.addAll(map.keySet());
    }

    public void clear()
    {
        this.map.clear();
        this.keys.clear();
    }
}
