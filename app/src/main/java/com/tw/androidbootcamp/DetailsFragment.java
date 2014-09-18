package com.tw.androidbootcamp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailsFragment extends Fragment {
    private static final String ARG_RESTAURANT_ID = "restaurantId";

    private long restaurantId;

    public static DetailsFragment newInstance(long restaurantId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getLong(ARG_RESTAURANT_ID);
        }
//               GoogleMap map = ((MapFragment) getFragmentManager()
//                .findFragmentById(R.id.location_map)).getMap();
//
//        LatLng sydney = new LatLng(-33.867, 151.206);
//
//        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView tvRestId = (TextView) view.findViewById(R.id.tv_details_rest_id);
        tvRestId.setText(String.format("You are viewing details for restaurant %s", restaurantId));

        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.location_map)).getMap();

        LatLng sydney = new LatLng(-33.867, 151.206);


        map.setMyLocationEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));

        return view;
    }


    public void updateView(long restaurantId) {
        TextView tvRestId = (TextView) getView().findViewById(R.id.tv_details_rest_id);
        tvRestId.setText(String.format("You are viewing details for restaurant %s", restaurantId));
    }
}
