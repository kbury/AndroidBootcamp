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
        String test = getIntent().getExtras().getString("TEST");

        setContentView(R.layout.activity_second);
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(test);
    }



}
