package com.example.mona.digitalrecipe.activities;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.fragments.ProfileFragment;
import com.example.mona.digitalrecipe.fragments.RecipeListFragment;
import com.example.mona.digitalrecipe.fragments.ShowDoctorsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AsyncTaskCallback{

    private Boolean isLoggedIn;
    private String userID;
    private Bundle bundleFragment;
    private RecipeListFragment recipeListFragment;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mona.digitalrecipe.R.layout.activity_home);

        //get Parameter from LoginActivity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("id");
            isLoggedIn = bundle.getBoolean("isLoggedIn");
        }
        //Log.d(TAG, "onCreate Extras: " + userID + ", " + isLoggedIn);

        //get Bottom navigation by id and add listener
        BottomNavigationView bottomNav = findViewById(com.example.mona.digitalrecipe.R.id.bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //prepare bundle for pass parameter from activity to fragment
        bundleFragment = new Bundle();
        bundleFragment.putString("id", userID);

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        String type = "getRequires";
        String userrole = "Patienten";
        backgroundHandler.execute(type, userrole, userID);
    }

    //set Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //Log.d(TAG, "ItemSelectListener");
                    Fragment selectedFragment = null;

                    //check which item was clicked and create associated fragment
                    switch (item.getItemId()){
                        case com.example.mona.digitalrecipe.R.id.nav_recipe:
                            selectedFragment =  createRecipeListFragments();
                            break;
                        case com.example.mona.digitalrecipe.R.id.nav_doc:
                            selectedFragment = new ShowDoctorsFragment();
                            break;
                        case com.example.mona.digitalrecipe.R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(bundleFragment);
                            break;
                    }

                    //show associated fragment
                    getSupportFragmentManager().beginTransaction().replace(com.example.mona.digitalrecipe.R.id.fragment_container_id, selectedFragment).commit();

                    return true;
                }
            };

    //declaration of new Fragment and transfer the parameter from the HomeActivity
    private RecipeListFragment createRecipeListFragments(){
        recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundleFragment);
        return recipeListFragment;
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {

        String jsonID = "";

        if(type.equals("getRequires")){
            //Log.d(TAG, "Interface getAsyncResult");
            //callback result with Requires
            try {
                //get the id from the object index 0 (API just return one object with id for Login)
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //transfer Array to RequireFragment, first convert to Arraylist
                    ArrayList<String> requireList = new ArrayList<>();

                    //add objects from jasonArray in arrayList
                    for(int i = 0; i < jsonArray.length(); i++){
                        requireList.add(jsonArray.getString(i));
                        //Log.d(TAG, "Interface getAsyncResult get String: " + jsonArray.getString(i)); //Test output
                    }
                    bundleFragment.putStringArrayList("requireArray", requireList);

                    //set start fragment
                    getSupportFragmentManager().beginTransaction().replace(com.example.mona.digitalrecipe.R.id.fragment_container_id, createRecipeListFragments()).commit();

                }else{
                    //no Require in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: No Requires"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }


    }
}
