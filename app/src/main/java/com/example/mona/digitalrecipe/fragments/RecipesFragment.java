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
    View itemView;
    ArrayList<Recipe> recipeList;
    private String userID = "";
    private ListView listView;
    private Context context;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "RecipesFragment"; //TAG for test outputs

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView"); //Test output
        view = inflater.inflate(R.layout.fragment_recipes, container, false);

        listView = view.findViewById(R.id.recipeListView);
        context = container.getContext();

        //get transfered parameter from HomeActivity

        if(getArguments() != null){
            userID = getArguments().getString("id");
           // ArrayList<String> jsonList = getArguments().getStringArrayList("requireArray");
            Log.d(TAG, "onCreateView get Arguments: " + userID ); //Test output
            //setListViewContent(jsonList);
        }

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        String type = "getRecipes";
        String userrole = "Patienten";
        backgroundHandler.execute(type, userrole, userID);

        //setListViewContent();
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: refresh data"); //Test output

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
        //ArrayList<String> arrayList parameter in function maybe
        //initialise arraylist and add requires
        /*final ArrayList<Recipe>*/ recipeList = new ArrayList<>();
        Log.d(TAG, "setListViewContent "); //Test output

        /*for(int i = 0; i < arrayList.size(); i++){
            try {
                //create JSON
                JSONObject jsonObject = new JSONObject(arrayList.get(i));

                String user = ( String) jsonObject.get("ver_nummer");
                String doctor = ( String) jsonObject.get("LANR_fk");
                String medicine = ( String) jsonObject.get("med_name");
                String medicinePortion = (String) jsonObject.get("med_menge");

                Log.d(TAG, "setListViewContent: user, doctor ,medicine, portion: " + user + ", " + doctor +", " + medicine + ", " + medicinePortion); //Test output

                Recipe recipe = new Recipe(user, medicine, medicinePortion, doctor);
                recipeList.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/

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

                Log.d(TAG, "setListViewContent:  doctor ,medicine, portion: " +  ", " + doctor +", " + medicine + ", " + medPortion); //Test output

                Recipe recipe = new Recipe(patID, docID, docTitle, doctor, recipeID, medicine, medForm, medPortion, medDate, noctu, autIdem);
                recipeList.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

       /* //Test recipes
        Recipe recipe1 = new Recipe("Mona", "Patropazol", "30", "Dr.Winter");
        Recipe recipe2 = new Recipe("Lisa", "Aspirin", "30", "Dr.Sommer");
        Recipe recipe3 = new Recipe("Doro", "Schnaps", "1", "Dr.Herbst");
        Recipe recipe4 = new Recipe("Norbert", "Patropazol", "30", "Dr.Bodo");
        Recipe recipe5 = new Recipe("Lisa", "Patropazol", "30", "Dr.Winter");
        Recipe recipe6 = new Recipe("Doro", "Schlaf", "8h", "Dr.Sommer");
        Recipe recipe7 = new Recipe("Norbert", "Patropazol", "30", "Dr.Winter");
        Recipe recipe8 = new Recipe("Mona", "Patropazol", "30", "Dr.Eckmann");

        recipeList.add(recipe1);
        recipeList.add(recipe2);
        recipeList.add(recipe3);
        recipeList.add(recipe4);
        recipeList.add(recipe5);
        recipeList.add(recipe6);
        recipeList.add(recipe7);
        recipeList.add(recipe8);*/

        //Log.d(TAG, "setListViewContent null test: context, list: " + context + ", " + recipeList); //Test output

        //Adapter declaration
        RecipeListAdapter recipeAdapter = new RecipeListAdapter(context, R.layout.list_item_recipe, recipeList);
        //Log.d(TAG, "setListViewContent null test: adapter, layout: " + recipeAdapter + ", " + R.layout.list_item_recipe); //Test output
        listView.setAdapter(recipeAdapter);

        //set ClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onClickItem: " + recipeList.get(i).getMedicine());
                //Toast.makeText(context, recipeList.get(i).getPatientsName(), Toast.LENGTH_LONG);

                /*Intent intent = new Intent(this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", jsonID);
                bundle.putBoolean("isLoggedIn", jsonIsUser);
                intent.putExtras(bundle);
                startActivity(intent);*/

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

                    /* //transfer Array to RecipeFragment, first convert to Arraylist
                    ArrayList<String> recipeList = new ArrayList<>();

                    //add objects from jasonArray in arrayList
                    for(int i = 0; i < jsonArray.length(); i++){
                        recipeList.add(jsonArray.getString(i));
                        //Log.d(TAG, "Interface getAsyncResult get String: " + jsonArray.getString(i)); //Test output
                    }*/


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









