package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

        if(findViewById(R.id.left_fragment)== null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder, listFragment, TAG_FRAGMENT_LIST)
                    .addToBackStack(TAG_FRAGMENT_LIST)
                    .commit();
        }

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onFragmentInteraction(long restaurantId) {
        Log.i(LOG_TAG, "onFragmentInteraction");

        if(findViewById(R.id.left_fragment)== null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder, DetailsFragment.newInstance(restaurantId))
                    .addToBackStack(TAG_FRAGMENT_DETAILS)
                    .commit();
        }else{
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.right_fragment);
            details.updateView(restaurantId);

        }

    }
}
