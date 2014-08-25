package com.tw.androidbootcamp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends Activity implements BlankFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final String[] titles = new String[]{"List Item 1", "List Item 2", "List Item 3", "List Item 4",
                "List Item 5", "List Item 6", "List Item 7", "List Item 8", "List Item 9"};

        String[] subtitles = new String[]{"Subtitle 1", "Subtitle 2", "Subtitle 3", "Subtitle 4",
                "Subtitle 5", "Subtitle 6", "Subtitle 7", "Subtitle 8", "Subtitle 9"};

        ListView listView = (ListView) findViewById(R.id.main_list_view);

        ListAdapter listAdapter = new ArrayAdapter(MainActivity.this, R.layout.list_item, R.id.title, titles);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("title", titles[i]);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        // switch fragment A for fragment B
    }
}
