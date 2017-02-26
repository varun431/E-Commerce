package com.example.varun.cyclemanufacturer.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Varun on 15-Jan-17.
 */

public class ProductModel {

    String name;
    String price;
    int imageId;
    float rating;
    int quantity = 1;

    public ProductModel(String name, String price, float rating, int imageId) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
