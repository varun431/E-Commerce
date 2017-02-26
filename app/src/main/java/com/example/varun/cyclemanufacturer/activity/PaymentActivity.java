package com.example.varun.cyclemanufacturer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.varun.cyclemanufacturer.Application.GlobalCart;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.adapters.CartReviewAdapter;
import com.example.varun.cyclemanufacturer.model.ProductModel;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    ArrayList<ProductModel> cartData;
    CartReviewAdapter adapter;
    TextView total_amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final ListView listView = (ListView) findViewById(R.id.cart_review);
        total_amt = (TextView) findViewById(R.id.total_amt);

        cartData = ((GlobalCart)getApplicationContext()).getArrayList();
        adapter = new CartReviewAdapter(getApplicationContext(), cartData);
        listView.setAdapter(adapter);

        total_amt.setText(String.valueOf("$" + adapter.totalAmount()));
    }
}
