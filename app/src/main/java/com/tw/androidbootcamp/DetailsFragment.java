package com.tw.androidbootcamp;

import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tw.androidbootcamp.model.Restaurant;
import com.tw.androidbootcamp.services.PictureService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        setupImageCaptureView(view);

        setupMapView();

        return view;
    }

    private void setupMapView() {
        double lat = 0.0, lng = 0.0;

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();

        Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);

        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(restaurant.getAddress(), 1);
            if (addresses.size() > 0) {
                lat = addresses.get(0).getLatitude();
                lng = addresses.get(0).getLongitude();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        LatLng latlng = new LatLng(lat, lng);
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));

        map.addMarker(new MarkerOptions().position(latlng));
    }

    private void setupImageCaptureView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.restaurant_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    public void updateView(long restaurantId) {
//        TextView tvRestId = (TextView) getView().findViewById(R.id.tv_details_rest_id);
//        tvRestId.setText(String.format("You are viewing details for restaurant %s", restaurantId));
    }

    public void takePicture() {
        PictureService service = new PictureService(getActivity());
        service.takePicture();

        Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);
        restaurant.setImageUrl(service.getPath());
        restaurant.save();
    }
}
