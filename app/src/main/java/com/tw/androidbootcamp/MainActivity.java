package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;


public class MainActivity extends Activity implements InteractionListeners.OnFragmentInteractionListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String TAG_FRAGMENT_LIST = "fragment_list";
    private static final String TAG_FRAGMENT_DETAILS = "fragment_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListFragment listFragment = (ListFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT_LIST);
        if (null == listFragment) {
            listFragment = new ListFragment();
        }

        if(findViewById(R.id.new_layout1)== null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder, listFragment, TAG_FRAGMENT_LIST)
                    .commit();
        }

    }

    @Override
    public void onFragmentInteraction(long restaurantId) {
        Log.i(LOG_TAG, "onFragmentInteraction");

        if(findViewById(R.id.new_layout1)== null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder, DetailsFragment.newInstance(restaurantId))
                    .commit();
        }else{
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.new_layout2);
            details.updateView(restaurantId);

        }

    }
}
