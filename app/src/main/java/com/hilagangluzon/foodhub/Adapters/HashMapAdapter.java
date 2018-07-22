package com.hilagangluzon.foodhub.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapAdapter extends BaseAdapter
{
    private Context context;
    private int resource;
    private HashMap<String, Object> map;
    private ArrayList<String> keys;

    public HashMapAdapter()
    {
        this.map = new HashMap<>();
        this.keys = new ArrayList<>();
    }

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
        this.keys = new ArrayList<>();
        this.keys.addAll(map.keySet());
    }

    @Override
    public int getCount() {
        return getMap().size();
    }

    @Override
    public Object getItem(int i) {
        return getMap().get(keys.get(i));
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
        this.getMap().put(key, value);
        this.keys.clear();
        //this.keys = new ArrayList<>();
        this.keys.addAll(getMap().keySet());
    }

    public void set(String key, Object value)
    {
        if(getMap().containsKey(key))
        {
            //this.keys.add(key);
            this.getMap().put(key, value);
            this.keys.clear();
            //this.keys = new ArrayList<>();
            this.keys.addAll(getMap().keySet());
        }
    }

    public void remove(String key)
    {
        //this.keys.remove(key);
        this.getMap().remove(key);
        this.keys.clear();
        //this.keys = new ArrayList<>();
        this.keys.addAll(getMap().keySet());
    }

    public void clear()
    {
        this.map.clear();
        this.keys.clear();
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void changeDataSet(HashMap<String, Object> map) {
        clear();
        this.map = map;
        this.keys.addAll(map.keySet());
        notifyDataSetChanged();
    }
}
