package com.tw.androidbootcamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kbury on 21/08/2014.
 */
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
        return restaurantList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = inflater.inflate(R.layout.list_item_restaurant, viewGroup, false);

        Restaurant restaurant = (Restaurant) getItem(position);
        ((TextView)view.findViewById(R.id.tv_item_rest_id)).setText(String.valueOf(restaurant.getId()));
        ((TextView)view.findViewById(R.id.tv_item_rest_name)).setText(String.valueOf(restaurant.getName()));

        view.setTag(restaurant);

        return view;
    }
}
