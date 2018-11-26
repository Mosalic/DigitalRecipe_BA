package com.example.mona.digitalrecipe;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get Bottom navigation by id and add listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //set start fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeListFragment()).commit();

    }

    //set Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    //check which item was clicked and create associated fragment
                    switch (item.getItemId()){
                        case R.id.nav_recipe:
                            selectedFragment = new RecipeListFragment();
                            break;
                        case R.id.nav_doc:
                            selectedFragment = new ShowDoctorsFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    //show associated fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true; //select item
                }
            };
}
