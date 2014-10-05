package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListFragment extends Fragment {

    private static final String LOG_TAG = ListFragment.class.getSimpleName();

    private InteractionListeners.OnFragmentInteractionListener mListener;
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    private RestaurantListAdapter adapter;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RestaurantListAdapter(getActivity(), restaurants);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://jsonplaceholder.typicode.com")
                .build();

        List<Restaurant> retrievedRestList = new Select().from(Restaurant.class).execute();
        if(retrievedRestList.isEmpty())
        {
            MyService service = restAdapter.create(MyService.class);
            service.getRestaurants(getCallback());
        }
        else {
            updateAdapter(retrievedRestList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Restaurant restaurant = (Restaurant) view.getTag();
                onRestaurantClicked(restaurant);
            }
        });

        return view;
    }

    public void onRestaurantClicked(Restaurant restaurant) {
        if (mListener != null) {
            Log.i(LOG_TAG, "onRestaurantClicked");

            mListener.onFragmentInteraction(restaurant);
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

                SaveRestaurantsToDb(restaurants);
                updateAdapter(restaurants);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(LOG_TAG, "Error", retrofitError);
            }
        };
    }

    private void updateAdapter(List<Restaurant> restaurants)  {
        adapter.setRestaurants(restaurants);
        adapter.notifyDataSetChanged();
    }

    private void SaveRestaurantsToDb(List<Restaurant> restaurants) {
        ActiveAndroid.beginTransaction();
        try {
            for (Restaurant rest : restaurants) {

                rest.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

}
