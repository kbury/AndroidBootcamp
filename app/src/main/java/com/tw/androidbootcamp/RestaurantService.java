package com.tw.androidbootcamp;

import com.tw.androidbootcamp.model.Restaurant;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface RestaurantService {
    @GET("/albums")
    void getRestaurants(Callback<List<Restaurant>> callback);
}
