package com.example.varun.cyclemanufacturer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.model.ProductModel;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Varun on 15-Jan-17.
 */

public class ProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<ProductModel> data = null, cartData, filteredData;

    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList, ArrayList<ProductModel> cartData) {
        this.context = context;
        this.cartData = cartData;
        this.data = arrayList;
        this.filteredData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ProductModel model = (ProductModel) getItem(position);
        View v = convertView;

        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(
                    R.layout.item_list, null);
            viewHolder = new ViewHolder();
            initializeViews(viewHolder, v);

            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }


        if (model != null) {

            if (viewHolder.name != null) {
                viewHolder.name.setText(model.getName());
            }

            if (viewHolder.ratingBar != null) {
                viewHolder.ratingBar.setRating(model.getRating());
            }

            if (viewHolder.price != null) {
                viewHolder.price.setText(model.getPrice());
            }

            if (viewHolder.pic != null) {
                String id = String.valueOf(model.getImageId());
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cycloid-391ab.appspot.com")
                        .child(String.valueOf(id + "/image.jpg"));
                Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(viewHolder.pic);
            }
        }

        viewHolder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cartData.contains(model)) {
                    cartData.add(model);
                    Toast.makeText(context, "Item has been added to your cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "This item is already there in your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data.clear();
        if (charText.length() == 0) {
            data.addAll(filteredData);
        }
        else
        {
            for (ProductModel wp : filteredData)
            {
                if (wp.getName().toLowerCase().contains(charText))
                {
                    data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void initializeViews(ViewHolder viewhold, View convertView) {
        viewhold.name = (TextView) convertView.findViewById(R.id.name);
        viewhold.ratingBar = (RatingBar) convertView.findViewById(R.id.rating);
        viewhold.price = (TextView) convertView.findViewById(R.id.price);
        viewhold.pic = (ImageView) convertView.findViewById(R.id.pic);
        viewhold.addtocart = (Button) convertView.findViewById(R.id.addtocart);
    }

    private static class ViewHolder {

        Button addtocart;
        TextView name, price;
        RatingBar ratingBar;
        ImageView pic;
    }

    public void updateFilteredData() {
        filteredData.addAll(data);
    }
}
