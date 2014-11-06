package com.tw.androidbootcamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tw.androidbootcamp.model.Restaurant;

import java.util.List;

public class RestaurantListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Restaurant> restaurantList;

    public RestaurantListAdapter(Context context, List<Restaurant> restaurantList) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Restaurant restaurant = restaurantList.get(position);

        if (restaurant != null && restaurant.getId() != null) {
            return restaurant.getId();
        } else {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = inflater.inflate(R.layout.list_item_restaurant, viewGroup, false);

        Restaurant restaurant = (Restaurant) getItem(position);
        ((TextView) view.findViewById(R.id.restaurant_name)).setText(String.valueOf(restaurant.getName()));
        ((TextView) view.findViewById(R.id.restaurant_address)).setText(String.valueOf(restaurant.getAddress()));

        view.setTag(restaurant);

        return view;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        restaurantList.clear();

        for (Restaurant r : restaurants) {
            r.save();
        }
        restaurantList.addAll(restaurants);
    }
}
