package com.example.firebasepicture;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.util.Log;

import java.net.URL;

//Информация об записи в firebase
public class Model {
    String pic;
    String title;
    String price;
    String name;

    public Model(){
    }

    public Model(String pic, String title, String price, String name) {
        this.pic = pic;
        this.title = title;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
