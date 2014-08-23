package com.tw.androidbootcamp;

/**
 * Created by kbury on 21/08/2014.
 */
public class Restaurant {

    private long id;
    private String name;

    public Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
