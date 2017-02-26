package com.example.varun.cyclemanufacturer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.varun.cyclemanufacturer.Application.GlobalCart;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.adapters.CartAdapter;
import com.example.varun.cyclemanufacturer.model.ProductModel;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<ProductModel> cartData;
    CartAdapter adapter;
    ImageView back, buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final ListView listView = (ListView) findViewById(R.id.cart_list_item);
        cartData = ((GlobalCart)getApplication()).getArrayList();
        adapter = new CartAdapter(getApplicationContext(), cartData);
        listView.setAdapter(adapter);

        back = (ImageView) findViewById(R.id.textView1w);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buy = (ImageView) findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.dataSetChanged();
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
