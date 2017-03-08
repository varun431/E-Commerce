package com.example.varun.cyclemanufacturer.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.varun.cyclemanufacturer.R;
import com.example.varun.cyclemanufacturer.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends FragmentActivity {

    ImageView iv;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iv = (ImageView) findViewById(R.id.profile_pic);

        Glide.with(getApplicationContext()).load(R.drawable.background).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setIndicatorColor(Color.parseColor("#C84B46"));
        tabs.setViewPager(pager);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = { "Photos", "Follower", "Following",
                "Like" };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println(position);
            if (position == 0)
                return ProfileFragment.newInstance(0);
            else if (position == 1)
                return ProfileFragment.newInstance(1);
            else if (position == 2)
                return ProfileFragment.newInstance(2);
            else
                return ProfileFragment.newInstance(3);

        }
    }
}
