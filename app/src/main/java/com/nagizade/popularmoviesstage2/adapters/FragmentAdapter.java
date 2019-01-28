package com.nagizade.popularmoviesstage2.adapters;

/**
 * Created by Hasan Nagizade on 18 January 2019
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nagizade.popularmoviesstage2.R;
import com.nagizade.popularmoviesstage2.fragments.Favorites;
import com.nagizade.popularmoviesstage2.fragments.PopularFragment;
import com.nagizade.popularmoviesstage2.fragments.TopRated;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularFragment();
        } else if (position == 1){
            return new TopRated();
        } else {
            return new Favorites();
        }
    }

    // Number of tabs our ViewPager will have
    @Override
    public int getCount() {
        return 3;
    }

    // Titles for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.popular_movies_title);
            case 1:
                return mContext.getString(R.string.top_rated_movies_title);
            case 2:
                return mContext.getString(R.string.favorite_movies_title);
            default:
                return null;
        }
    }

}
