package com.example.varun.cyclemanufacturer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.activity.CartActivity;
import com.example.varun.cyclemanufacturer.model.ProductModel;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Varun on 16-Jan-17.
 */

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> data;

    public CartAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.data = arrayList;
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
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ProductModel model = (ProductModel) getItem(position);
        View v = convertView;

        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(
                    R.layout.cart_item_list, null);
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

            if(viewHolder.pic != null) {
                String id = String.valueOf(model.getImageId());
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cycloid-391ab.appspot.com")
                        .child(String.valueOf(id + "/image.jpg"));
                Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(viewHolder.pic);
            }

            if(viewHolder.quantity != null) {
                String tmp = ((EditText) v.findViewById(R.id.quantity)).getText().toString();
                if(!tmp.equals("")) {
                    ((ProductModel)getItem(position)).setQuantity(Integer.parseInt(tmp));
                }
                else if(tmp.equals("0")) {
                    delete_item(position);
                }
            }
        }

        viewHolder.delete_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_item(position);
                Toast.makeText(context, "Item has been deleted from your cart", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void initializeViews(ViewHolder viewhold, View convertView) {
        viewhold.name = (TextView) convertView.findViewById(R.id.name);
        viewhold.ratingBar = (RatingBar) convertView.findViewById(R.id.rating);
        viewhold.price = (TextView) convertView.findViewById(R.id.price);
        viewhold.pic = (ImageView) convertView.findViewById(R.id.pic);
        viewhold.delete_from_cart = (Button) convertView.findViewById(R.id.deletefromcart);
        viewhold.quantity = (EditText) convertView.findViewById(R.id.quantity);
    }

    private static class ViewHolder {
        Button delete_from_cart;
        TextView name, price;
        RatingBar ratingBar;
        ImageView pic;
        EditText quantity;
    }

    public void dataSetChanged() {
        notifyDataSetChanged();
    }

    private void delete_item(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }
}
