package com.example.varun.cyclemanufacturer.Application;

import android.app.Application;
import android.widget.Toast;

import com.example.varun.cyclemanufacturer.model.ProductModel;

import java.util.ArrayList;

/**
 * Created by Varun on 23-Jan-17.
 */

public class GlobalCart extends Application {

    private ArrayList<ProductModel> cartData = new ArrayList<>();

    public ArrayList<ProductModel> getArrayList() {
        return cartData;
    }
}
