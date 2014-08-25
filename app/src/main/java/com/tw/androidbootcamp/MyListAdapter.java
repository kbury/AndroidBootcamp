package com.tw.androidbootcamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    String[] titles;
    String[] subtitles;

    public MyListAdapter(Context context, String[] titles, String[] subtitles, LayoutInflater inflater) {
        super(context, R.layout.list_item);
        this.titles = titles;
        this.subtitles = subtitles;
        this.inflater = inflater;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public String getItem(int position) {
        return titles[position];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.list_item, viewGroup);

        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(titles[i]);

        TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
        subtitle.setText(subtitles[i]);

        return v;
    }
}
