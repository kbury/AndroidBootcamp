package com.tw.androidbootcamp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.content.Context;

/**
 * Created by astefa on 21/08/2014.
 */
public class MyAdapter extends ArrayAdapter<String> {

    LayoutInflater inflater;
    Context context;

    String[] list = {"bla", "2"};

    public MyAdapter(Context context,String[] values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.list = values;
        inflater= (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public String getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.list_item, viewGroup,false);
        TextView tv = (TextView)v;
        tv.setText(list[position]);

        return v;
    }
}
