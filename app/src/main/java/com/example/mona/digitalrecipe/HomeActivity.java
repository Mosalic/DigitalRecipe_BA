package com.example.mona.digitalrecipe;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate"); //Test output

        //get Bottom navigation by id and add listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //set start fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_id, new RecipeListFragment()).commit();

    }

    //set Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Log.d(TAG, "ItemSelectListener"); //Test output
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_id, selectedFragment).commit();

                    return true; //select item
                }
            };
}
