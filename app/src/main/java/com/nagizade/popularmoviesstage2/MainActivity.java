package com.nagizade.popularmoviesstage2;

/**
 * Created by Hasan Nagizade on 14 January 2019
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.nagizade.popularmoviesstage2.adapters.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout   tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        FragmentAdapter fragmentAdapter = new FragmentAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void bindViews() {
        viewPager           = findViewById(R.id.view_pager);
        tabLayout           = findViewById(R.id.tabLayout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
