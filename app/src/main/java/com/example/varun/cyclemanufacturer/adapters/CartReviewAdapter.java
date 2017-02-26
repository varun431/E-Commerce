package com.example.varun.cyclemanufacturer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.model.ProductModel;

import java.util.ArrayList;

/**
 * Created by Varun on 24-Jan-17.
 */

public class CartReviewAdapter extends BaseAdapter {

    Context context;
    ViewHolder viewHolder;
    ArrayList<ProductModel> data;
    float total_amt = 0;

    public CartReviewAdapter(Context context, ArrayList<ProductModel> data) {
        this.context = context;
        this.data = data;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductModel model = (ProductModel) getItem(position);
        View v = convertView;
        float amt = 0;

        if(v == null) {
            v = LayoutInflater.from(context).inflate(
                    R.layout.cart_review, null);
            viewHolder = new ViewHolder();
            initializeViews(viewHolder, v);

            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        if(model!=null) {
            viewHolder.quantity.setText(model.getQuantity() + "x");
            viewHolder.name.setText(model.getName());
            amt = Float.parseFloat(model.getPrice())*(model.getQuantity());
            viewHolder.price.setText("$" + amt);
        }

        return v;
    }

    private void initializeViews(ViewHolder viewhold, View convertView) {
        viewhold.name = (TextView) convertView.findViewById(R.id.product_name);
        viewhold.price = (TextView) convertView.findViewById(R.id.product_price);
        viewhold.quantity = (TextView) convertView.findViewById(R.id.product_quantity);
    }

    private static class ViewHolder {
        TextView name, price, quantity;
    }

    public float totalAmount() {
        for (ProductModel pm: data) {
            total_amt += Float.parseFloat(pm.getPrice())*(pm.getQuantity());
        }
        return total_amt;
    }
}
