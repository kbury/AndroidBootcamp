package com.tw.androidbootcamp;

import android.net.Uri;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by kbury on 21/08/2014.
 */
@Table(name = "Restaurants")
public class Restaurant extends Model implements Serializable{

    @Column(name = "RestId")
    public long id;

    @Column(name = "Title")
    public String title;

    @Column(name = "UserId")
    public long userId;

    @Column(name = "ImgUrl")
    public String imgUrl;

    public Restaurant() {
        super();
    }

    public Restaurant(long id, String title) {
        super();
        this.id = id;
        this.title = title;
    }

//    public long getId() {
//        return id;
//    }

    public String getTitle() {
        return title;
    }

    public long getUserId() {
        return userId;
    }

    public Uri getImgUrl() {
        return imgUrl != null ? Uri.parse(imgUrl) : null;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl.getPath();
    }

}
