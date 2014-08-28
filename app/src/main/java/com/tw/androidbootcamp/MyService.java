package com.tw.androidbootcamp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by kbury on 27/08/2014.
 */
public interface MyService {
    @GET("/albums")
    void getRestaurants(Callback<List<Restaurant>> callback);
}
