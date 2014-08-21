package com.tw.androidbootcamp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String test = "Hello World";

        try {
            test = getIntent().getExtras().getString("TEST");
        } catch (Exception e) {

        }

        setContentView(R.layout.activity_second);
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(test);
    }



}
