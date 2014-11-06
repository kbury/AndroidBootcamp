package com.tw.androidbootcamp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class DetailsFragment extends Fragment {
    private static final String ARG_RESTAURANT_ID = "restaurantId";
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    private long restaurantId;

    public static DetailsFragment newInstance(long restaurantId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (getArguments() != null) {
            restaurantId = getArguments().getLong(ARG_RESTAURANT_ID);
            Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);
            actionBar.setTitle(restaurant.getName());

            Activity context = getActivity();
            int distance = Math.round(getDistance(restaurant))/1000;
            createNotification(restaurant, context, distance);

        }
    }

    private void createNotification(Restaurant restaurant, Activity context, int distance) {
        int notificationId = 001;

        NotificationCompat.Action voiceAction = getVoiceAction();
        NotificationCompat.Action mapAction = getMapAction(restaurant, context);

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent aResultIntent = new Intent(context, MainActivity.class);
        PendingIntent aPendingIntent = PendingIntent.getActivity(context, 0, aResultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent bResultIntent = new Intent(context, MainActivity.class);
        PendingIntent bPendingIntent = PendingIntent.getActivity(context, 0, bResultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(restaurant.getName())
                        .setContentText("You are " + distance + "km away!")
                        .setVibrate(new long[]{800, 800})
                        .setLargeIcon(BitmapFactory.decodeFile(restaurant.getImageUrl()))
                        .addAction(android.R.drawable.ic_dialog_email, getString(R.string.map), aPendingIntent)
                        .addAction(android.R.drawable.ic_lock_lock, getString(R.string.map), bPendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addActions(Arrays.asList(mapAction, voiceAction)));


        notificationBuilder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private NotificationCompat.Action getMapAction(Restaurant restaurant, Activity context) {
        String uri = "http://maps.google.com/maps?mode=driving&daddr=" + restaurant.getAddress();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        PendingIntent mapPendingIntent = PendingIntent.getActivity(context, 0, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action.Builder(android.R.drawable.ic_dialog_map,
                getString(R.string.map), mapPendingIntent)
                .build();
    }

    private NotificationCompat.Action getVoiceAction() {
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY).build();
        Intent replyIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(getActivity(), 0, replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action.Builder(android.R.drawable.ic_btn_speak_now,
                getString(R.string.label), replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

    private float getDistance(Restaurant restaurant) {
        Location restaurantLocation = new Location("Restaurant");
        if(restaurant.getLatitude() == 0) {
            LatLng latLng = getRestaurantLatLng(restaurant);
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
        Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);

        setupImageCaptureView(view);
        setupMapView(restaurant);
        setupImageView(view, restaurant);

        return view;
    }

    private void setupImageView(View view, Restaurant restaurant) {
        ImageView imageView = (ImageView) view.findViewById(R.id.restaurant_image);
        if(restaurant.getImageUrl() != null) {
            imageView.setImageURI(Uri.parse(restaurant.getImageUrl()));
        }
    }

    private void setupMapView(Restaurant restaurant) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        LatLng latlng = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());

        if (restaurant.getLatitude() == 0) {
            latlng = getRestaurantLatLng(restaurant);
        }

        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));

        map.addMarker(new MarkerOptions().position(latlng));
        restaurant.setLatitude(latlng.latitude);
        restaurant.setLongitude(latlng.longitude);
    }

    private LatLng getRestaurantLatLng(Restaurant restaurant) {
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
                view.invalidate();
            }
        });
    }

    public void takePicture() {
        Restaurant restaurant = Restaurant.load(Restaurant.class, restaurantId);
        PictureService service = new PictureService(getActivity(), restaurant.getName());
        service.takePicture();

        restaurant.setImageUrl(service.getPath());
        restaurant.save();
    }
}
