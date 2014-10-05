package com.tw.androidbootcamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.net.URL;

/**
 * Created by kbury on 25/09/2014.
 */
public class Helpers {

    public static final String PREFS_NAME = "MyPrefsFile";

    public static boolean Save(Context context, Restaurant restaurant) {
        // Create a stringified json representation of the restaurant
//        Gson gson = new Gson();
//        String json = gson.toJson(restaurant);

        // Create preferences editor and add the restaurant
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        // MyPREFFILE
        // (1, "rest....")
        // (2, "rest...")

        // (Restaurants, "List<Restaurants>")


        SharedPreferences.Editor editor = settings.edit();
        editor.putString(String.valueOf(restaurant.getUserId()), restaurant.getImgUrl().getPath());

        // Commit the edits!
        editor.commit();

        return true;
    }

    public static Restaurant Load(Context context, Restaurant rest) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        String restaurantStr = settings.getString(String.valueOf(rest.getUserId()), "");

//        Gson gson = new Gson();
//        String imgUrl = gson.fromJson(restaurantStr, String.class);

        if (null != restaurantStr && !restaurantStr.isEmpty())
        rest.setImgUrl(Uri.parse(restaurantStr));
        return rest;
    }
}
