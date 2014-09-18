package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListFragment extends Fragment {

    private static final String LOG_TAG = ListFragment.class.getSimpleName();

    private InteractionListeners.OnFragmentInteractionListener mListener;
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private RestaurantListAdapter adapter;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://jsonplaceholder.typicode.com")
                .build();

        MyService service = restAdapter.create(MyService.class);

        service.getRestaurants(getCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        adapter = new RestaurantListAdapter(getActivity(), restaurantList);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Restaurant restaurant = (Restaurant) view.getTag();
                onRestaurantClicked(restaurant.getId());
            }
        });

        Button button = (Button)view.findViewById(R.id.seeMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholder, MarkerClusterFragment.newInstance(restaurantList), "fragment_marker_cluster")
                        .commit();
            }
        });

        return view;
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
                restaurantList = restaurants;
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
