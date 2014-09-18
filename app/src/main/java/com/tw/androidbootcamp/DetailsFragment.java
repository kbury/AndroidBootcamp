package com.tw.androidbootcamp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailsFragment extends Fragment {
    private static final String ARG_RESTAURANT_ID = "restaurantId";
    private static final int CAMERA_ACTIVITY = 1;

    private long restaurantId;
    private Uri fileUri;
    private ImageView ivPhoto;

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

        TextView tvRestId = (TextView) view.findViewById(R.id.tv_details_rest_id);
        tvRestId.setText(String.format("You are viewing details for restaurant %s", restaurantId));

        ivPhoto = (ImageView) view.findViewById(R.id.picture);
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = Uri.fromFile(getOutputMediaFile());

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAMERA_ACTIVITY);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();

        LatLng latlng = new LatLng(-33.863, 151.208);
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));

        map.addMarker(new MarkerOptions().position(latlng));

        return view;
    }

    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AndroidBootcamp");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("AndroidBootcamp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(new Date());

        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case CAMERA_ACTIVITY:
                if (ivPhoto != null) {
                    Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath());
                    ivPhoto.setImageBitmap(bmp);
                }
        }
    }

    public void updateView(long restaurantId) {
        TextView tvRestId = (TextView) getView().findViewById(R.id.tv_details_rest_id);
        tvRestId.setText(String.format("You are viewing details for restaurant %s", restaurantId));
    }
}
