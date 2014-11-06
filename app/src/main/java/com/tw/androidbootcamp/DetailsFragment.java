package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tw.androidbootcamp.model.Restaurant;
import com.tw.androidbootcamp.services.PictureService;

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
            Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);

            Activity context = getActivity();
            int distance = Math.round(getDistance(restaurant))/1000;
            createNotification(restaurant, context, distance);

        }
    }

    private void createNotification(Restaurant restaurant, Activity context, int distance) {
        int notificationId = 001;
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(restaurant.getName())
                        .setContentText("You are " + distance + "km away!")
                        .setVibrate(new long[]{800, 800});


        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private float getDistance(Restaurant restaurant) {
        Location restaurantLocation = new Location("Restaurant");
        if(restaurant.getLatitude() == 0) {
            LatLng latLng = getLatLng(restaurant);
            restaurant.setLatitude(latLng.latitude);
            restaurant.setLongitude(latLng.longitude);
            restaurant.save();
        }

        restaurantLocation.setLatitude(restaurant.getLatitude());
        restaurantLocation.setLongitude(restaurant.getLongitude());

        Location currentLocation = new Location("ThoughtWorks");
        currentLocation.setLatitude(-33.862983);
        currentLocation.setLongitude(151.208808);
        return currentLocation.distanceTo(restaurantLocation);
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
        Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        LatLng latlng = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());

        if (restaurant.getLatitude() == 0) {
            latlng = getLatLng(restaurant);
        }

        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));

        map.addMarker(new MarkerOptions().position(latlng));
        restaurant.setLatitude(latlng.latitude);
        restaurant.setLongitude(latlng.longitude);
    }

    private LatLng getLatLng(Restaurant restaurant) {
        LatLng latlng;
        double lat = 0.0, lng = 0.0;

        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(restaurant.getAddress(), 1);
            if (addresses.size() > 0) {
                lat = addresses.get(0).getLatitude();
                lng = addresses.get(0).getLongitude();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        latlng = new LatLng(lat, lng);
        return latlng;
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
