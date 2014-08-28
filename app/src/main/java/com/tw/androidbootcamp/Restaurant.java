package com.tw.androidbootcamp;

import java.io.Serializable;

/**
 * Created by kbury on 21/08/2014.
 */
public class Restaurant implements Serializable{

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

}
