package com.example.mona.digitalrecipe.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentListTitles = new ArrayList<>();
    private static final String TAG = "ViewPagerAdapter"; //TAG for test outputs

    //constructor
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        Log.d(TAG, "Constructor"); //Test output
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        Log.d(TAG, "addFragment: " + title); //Test output
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }
}
