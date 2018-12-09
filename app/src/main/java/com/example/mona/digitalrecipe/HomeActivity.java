package com.example.mona.digitalrecipe;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mona.digitalrecipe.Interfaces.AsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AsyncTaskCallback{

    private Boolean isLoggedIn;
    private String userID;
    private Bundle bundleFragment;
    private  RecipeListFragment recipeListFragment;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "HomeActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate"); //Test output

        //get Parameter from LoginActivity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("id");
            isLoggedIn = bundle.getBoolean("isLoggedIn");
        }
        Log.d(TAG, "onCreate Extras: " + userID + ", " + isLoggedIn); //Test output

        //get Bottom navigation by id and add listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_id);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //prepare bundle for pass parameter from activity to fragment
        bundleFragment = new Bundle();
        bundleFragment.putString("id", userID);
        //RecipeListFragment recipeListFragment;

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
                    Log.d(TAG, "ItemSelectListener"); //Test output
                    Fragment selectedFragment = null;

                    //check which item was clicked and create associated fragment
                    switch (item.getItemId()){
                        case R.id.nav_recipe:
                            selectedFragment =  createRecipeListFragments();//new RecipeListFragment();
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

    //declaration of new Fragment and transfer the parameter from the HomeActivity
    private RecipeListFragment createRecipeListFragments(){
        recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundleFragment);
        return recipeListFragment;
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        //when id arrives start new Activity and pass the id with it, stop the thread

        String jsonID = "";

        if(type.equals("getRequires")){
            Log.d(TAG, "Interface getAsyncResult"); //Test output
            //callback result with Requires
            //get the id from the object index 0 (API just return one object with id for Login)
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty

                    //transfer Array to RequireFragment, first convert to Arraylist
                    ArrayList<String> requireList = new ArrayList<>();

                    //add objects from jasonArray in arrayList
                    for(int i = 0; i < jsonArray.length(); i++){
                        requireList.add(jsonArray.getString(i));
                        //Log.d(TAG, "Interface getAsyncResult get String: " + jsonArray.getString(i)); //Test output
                    }
                    bundleFragment.putStringArrayList("requireArray", requireList);

                    //set start fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_id, createRecipeListFragments()).commit();

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
