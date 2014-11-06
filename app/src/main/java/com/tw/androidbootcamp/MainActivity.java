package com.tw.androidbootcamp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tw.androidbootcamp.services.PictureService;

public class MainActivity extends Activity implements InteractionListeners.OnFragmentInteractionListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String TAG_FRAGMENT_LIST = "fragment_list";
    private static final String TAG_FRAGMENT_DETAILS = "fragment_details";

    private final PictureService pictureService = new PictureService(this, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.list_fragment) == null) {
            RestaurantListFragment firstFragment = new RestaurantListFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, firstFragment, TAG_FRAGMENT_LIST)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void onFragmentInteraction(long restaurantId) {
        Log.i(LOG_TAG, "onFragmentInteraction");

        if (findViewById(R.id.list_fragment) == null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(getIntent().getExtras());

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, DetailsFragment.newInstance(restaurantId))
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.take_picture:
                pictureService.takePicture();
                return true;
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PictureService.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                return;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getFragmentManager().getBackStackEntryCount() == 0)
            finish();
    }
}
