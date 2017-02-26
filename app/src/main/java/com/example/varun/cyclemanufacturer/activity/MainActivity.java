package com.example.varun.cyclemanufacturer.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.varun.cyclemanufacturer.Application.GlobalCart;
import com.example.varun.cyclemanufacturer.adapters.ProductAdapter;
import com.example.varun.cyclemanufacturer.model.User;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.model.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ProductModel> model, cartData;
//    GlobalCart cartData;
    ProductAdapter adapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef, myRef1;
    Resources resources;
    ImageView logout, cart, profile;
    EditText inputSearch;
    ProgressBar progressBar;
    User value;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        model = new ArrayList<>();
        cartData = ((GlobalCart)getApplicationContext()).getArrayList();
        resources = getApplication().getResources();

        final ListView listView = (ListView) findViewById(R.id.list_item);

        myRef1 = FirebaseDatabase.getInstance().getReference("products");
        progressBar.setVisibility(View.VISIBLE);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = null, price = null;
                    float rating = (float) 0.0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if(snapshot1.getKey().equals("name")) {
                            name = snapshot1.getValue(String.class);
                        } else if (snapshot1.getKey().equals("price")) {
                            price = String.valueOf(snapshot1.getValue(Double.class));
                        } else if (snapshot1.getKey().equals("rating")) {
                            rating = snapshot1.getValue(float.class);
                        }
                    }
                    addProduct(name, price, rating, Integer.parseInt(snapshot.getKey()));
                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                adapter.updateFilteredData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new ProductAdapter(getApplicationContext(), model, cartData);
        listView.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(in);
                } else {
                    // User is signed out
                }
            }
        };

        profile = (ImageView) findViewById(R.id.textView1w);
        profile.setOnClickListener(this);

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                mFirebaseAuth.signOut();
                loadLogInView();
                break;

            case R.id.cart:
                if(!cartData.isEmpty()) {
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.textView1w:
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
        }
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void addProduct(String name, String price, float rating, int imageId) {
        model.add(new ProductModel(name, price, rating, imageId));
    }
}
