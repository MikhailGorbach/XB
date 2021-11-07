package com.example.firebasepicture;

import android.media.Image;

public class Model {
    Image picture;
    String title, price;

    public Model() {

    }

    public Model(Image picture, String price, String title) {
        this.picture = picture;
        this.title = title;
        this.price = price;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
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
