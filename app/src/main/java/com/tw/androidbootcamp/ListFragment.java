package com.tw.androidbootcamp;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String LOG_TAG = ListFragment.class.getSimpleName();

    private InteractionListeners.OnFragmentInteractionListener mListener;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        List<Restaurant> restaurantList = Arrays.asList(
            new Restaurant(0, "Rest0"),
            new Restaurant(1, "Rest1")
        );

        RestaurantListAdapter adapter = new RestaurantListAdapter(getActivity(), restaurantList);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Restaurant restaurant = (Restaurant) view.getTag();
                onRestaurantClicked(restaurant.getId());
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

}
