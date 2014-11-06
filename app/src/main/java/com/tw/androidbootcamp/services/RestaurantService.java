package com.tw.androidbootcamp.services;

import com.tw.androidbootcamp.model.Restaurant;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface RestaurantService {
    @GET("/restaurants")
    void getRestaurants(Callback<List<Restaurant>> callback);
}
