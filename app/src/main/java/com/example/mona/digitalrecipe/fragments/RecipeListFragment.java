package com.example.mona.digitalrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userID;
    private ArrayList<String> jsonList;
    private ViewPagerAdapter viewAdapter;
    private static final String TAG = "RecipeListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recipe_lists, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);

        RequiresFragment requiresFragment = new RequiresFragment();
        RecipesFragment recipesFragment = new RecipesFragment();


        //get transfered parameter from HomeActivity
        if(getArguments() != null){
            userID = getArguments().getString("id");
            jsonList = getArguments().getStringArrayList("requireArray");
            //Log.d(TAG, "onCreateView get Arguments: " + userID + ", " + jsonList);

            requiresFragment.setArguments(getArguments());
            recipesFragment.setArguments(getArguments());

        }

        //Adapter declaration
        viewAdapter = new ViewPagerAdapter(getFragmentManager());

        //adding Fragments
        viewAdapter.addFragment(recipesFragment, "Rezepte");
        viewAdapter.addFragment(requiresFragment, "Anforderungen");

        //adapter setup
        viewPager.setAdapter(viewAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
