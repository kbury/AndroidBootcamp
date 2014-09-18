package com.tw.androidbootcamp;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

/**
 * Created by kbury on 21/08/2014.
 */
public class Restaurant implements Serializable, ClusterItem{

    private long id;
    private String title;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private long userId;

    public Restaurant(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng((33.867-id)+10,(151.206-id)+100);
    }
}
