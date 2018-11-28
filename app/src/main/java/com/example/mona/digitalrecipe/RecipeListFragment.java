package com.example.mona.digitalrecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeListFragment extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_recipe_lists, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);
        viewAdapter = new ViewPagerAdapter(getFragmentManager());
        //adding Fragments
        viewAdapter.addFragment(new RequiresFragment(), "Anforderungen");
        viewAdapter.addFragment(new RecipesFragment(), "Rezepte");
        //adapter setup
        viewPager.setAdapter(viewAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
