package com.nagizade.popularmoviesstage2;

/**
 * Created by Hasan Nagizade on 14 January 2019
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.nagizade.popularmoviesstage2.adapters.FragmentAdapter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout   tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable(this)) {
            bindViews();
            FragmentAdapter fragmentAdapter = new FragmentAdapter(this,getSupportFragmentManager());
            viewPager.setAdapter(fragmentAdapter);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            Intent intent = new Intent(MainActivity.this,NoInternet.class);
            startActivity(intent);
        }
    }

    private void bindViews() {
        viewPager           = findViewById(R.id.view_pager);
        tabLayout           = findViewById(R.id.tabLayout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
