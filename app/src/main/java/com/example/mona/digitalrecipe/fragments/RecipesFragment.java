package com.example.mona.digitalrecipe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.activities.HomeActivity;
import com.example.mona.digitalrecipe.activities.NewRequireActivity;
import com.example.mona.digitalrecipe.activities.ShowRecipeActivity;
import com.example.mona.digitalrecipe.adapters.RecipeListAdapter;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipesFragment extends Fragment implements AsyncTaskCallback {

    View view;
    ArrayList<Recipe> recipeList;
    private String userID = "";
    private ListView listView;
    private Context context;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "RecipesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recipes, container, false);

        listView = view.findViewById(R.id.recipeListView);
        context = container.getContext();

        //get transfered parameter from HomeActivity
        if(getArguments() != null){
            userID = getArguments().getString("id");
            //Log.d(TAG, "onCreateView get Arguments: " + userID );
            //setListViewContent(jsonList);
        }

        String type = "getRecipes";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID);

        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Refresh tab data
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }

    private void setListViewContent(JSONArray jsonArray){

        //initialise arraylist and add requires
        recipeList = new ArrayList<>();


        for(int i = 0; i < jsonArray.length(); i++){
            try {
                //create JSON
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String patID = ( String) jsonObject.get("ver_nummer");
                String docID = ( String) jsonObject.get("LANR_fk");
                String recipeID = ( String) jsonObject.get("id");
                String medicine = ( String) jsonObject.get("med_name");
                String medForm = ( String) jsonObject.get("med_form");
                String medPortion = (String) jsonObject.get("med_menge");
                String medDate = ((String) jsonObject.get("med_datum")).substring(0, 10);  //DateFormat 2018-09-12 23:04 -> 2018-09-12
                String noctu = ( String) String.valueOf(jsonObject.get("noctu"));
                String autIdem = ( String) String.valueOf(jsonObject.get("aut_idem"));
                String docTitle = ( String) jsonObject.get("doc_title");
                String docFirstName = ( String) jsonObject.get("doc_firstName");
                String docLastName = ( String) jsonObject.get("doc_lastName");
                String doctor = docFirstName + " " + docLastName;

                //Log.d(TAG, "setListViewContent:  doctor ,medicine, portion: " +  ", " + doctor +", " + medicine + ", " + medPortion);

                Recipe recipe = new Recipe(patID, docID, docTitle, doctor, recipeID, medicine, medForm, medPortion, medDate, noctu, autIdem);
                recipeList.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        //Adapter declaration
        RecipeListAdapter recipeAdapter = new RecipeListAdapter(context, R.layout.list_item_recipe, recipeList);
        //Log.d(TAG, "setListViewContent null test: adapter, layout: " + recipeAdapter + ", " + R.layout.list_item_recipe); //Test output
        listView.setAdapter(recipeAdapter);

        //set ClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d(TAG, "onClickItem: " + recipeList.get(i).getMedicine());
                showRecipe(i, recipeList.get(i));
            }
        });
    }

    private void showRecipe(int i, Recipe recipe){
        Intent intent = new Intent(getActivity(), ShowRecipeActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }

    //get Result from BackgroundHandler
    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {

        String jsonID = "";

        if(type.equals("getRecipes")){
            Log.d(TAG, "Interface getAsyncResult"); //Test output
            //callback result with Recipes
            //get the id from the object index 0 (API just return one object with id for Login)
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    setListViewContent(jsonArray); //set Content

                }else{
                    //no Recipe in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: No Recipes"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }
    }
}









