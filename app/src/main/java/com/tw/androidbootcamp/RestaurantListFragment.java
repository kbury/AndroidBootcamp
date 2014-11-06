package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.tw.androidbootcamp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantListFragment extends ListFragment {

    private static final String LOG_TAG = RestaurantListFragment.class.getSimpleName();

    private InteractionListeners.OnFragmentInteractionListener mListener;
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    private RestaurantListAdapter adapter;

    public RestaurantListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint("http://jsonplaceholder.typicode.com")
//                .build();
//
//        RestaurantService service = restAdapter.create(RestaurantService.class);
//        service.getRestaurants(getCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        List<Restaurant> restaurantList = new Select().all().from(Restaurant.class).execute();
        if(restaurantList.size() == 0) {
            Restaurant restaurant1 = new Restaurant("Chur Burger", "48 Albion St, Surry Hills NSW 2010");
            Restaurant restaurant2 = new Restaurant("Mary's", "6 Mary St, Newtown NSW 2042");
            restaurant1.save();
            restaurant2.save();

            restaurants.add(restaurant1);
            restaurants.add(restaurant2);
        } else {
            for(Restaurant r : restaurantList) {
                restaurants.add(r);
            }
        }

        adapter = new RestaurantListAdapter(getActivity(), restaurants);

        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Restaurant restaurant = (Restaurant) v.getTag();
        onRestaurantClicked(restaurant.getId());
    }

    public void onRestaurantClicked(long restaurantId) {
        if (mListener != null) {
            Log.i(LOG_TAG, "onRestaurantClicked");

            mListener.onFragmentInteraction(restaurantId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InteractionListeners.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Callback<List<Restaurant>> getCallback() {
        return new Callback<List<Restaurant>>() {
            @Override
            public void success(List<Restaurant> restaurants, Response response) {
                Log.d(LOG_TAG, "Success");
                adapter.setRestaurants(restaurants);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(LOG_TAG, "Error", retrofitError);
            }
        };
    }

}
