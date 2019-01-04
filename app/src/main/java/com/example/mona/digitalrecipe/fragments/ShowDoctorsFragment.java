package com.example.mona.digitalrecipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.adapters.DoctorListAdapter;
import com.example.mona.digitalrecipe.adapters.RecipeListAdapter;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.models.Adress;
import com.example.mona.digitalrecipe.models.Doctor;
import com.example.mona.digitalrecipe.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowDoctorsFragment extends Fragment implements AsyncTaskCallback {

    View view;
    private ListView listView;
    private Context context;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "ShowDoctorsFragment"; //TAG for test outputs

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView"); //Test output

        view = inflater.inflate(R.layout.fragment_doctors, container, false);

        listView = view.findViewById(R.id.doctorListView);
        context = container.getContext();

        //get transfered parameter from HomeActivity
       /* String userID = "";
        if(getArguments() != null){
            userID = getArguments().getString("id");
            // ArrayList<String> jsonList = getArguments().getStringArrayList("requireArray");
            Log.d(TAG, "onCreateView get Arguments: " + userID ); //Test output
            //setListViewContent(jsonList);
        }*/

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        String type = "getDoctors";
        String userrole = "Patienten";
        String userID = "x"; //0 weil hier ID keine ROlle spielt, für alle Patienten gleich
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
        ArrayList<Doctor> doctorList = new ArrayList<>();
        Log.d(TAG, "setListViewContent "); //Test output

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                //create JSON
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //create and set Adress for Doc
                String idAdress = (String) jsonObject.get("id_adress");
                String adressStreet = (String) jsonObject.get("adress_street");
                String  adressStreetNr = (String) jsonObject.get("adress_street_nr");
                String adressPLZ = (String) jsonObject.get("adress_PLZ");
                String adressCity = (String) jsonObject.get("adress_city");
                Adress adress = new Adress(idAdress, adressStreet, adressStreetNr, adressPLZ, adressCity);

                //set other doc infos
                String office = (String) jsonObject.get("doc_office_name");
                String phoneNumber = (String) jsonObject.get("office_phone");
                String title = (String) jsonObject.get("doc_title");
                String firstName = (String) jsonObject.get("doc_firstName");
                String lastName = (String) jsonObject.get("doc_lastName");

                Log.d(TAG, "setListViewContent: office, phoneNumber, lastName: " + office + ", " + phoneNumber +", " + lastName); //Test output

                Doctor doctor = new Doctor(firstName, lastName, title, office, phoneNumber, adress);
                doctorList.add(doctor);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Adapter declaration
        DoctorListAdapter doctorAdapter = new DoctorListAdapter(context, R.layout.list_item_doctor, doctorList);
        //Log.d(TAG, "setListViewContent null test: adapter, layout: " + recipeAdapter + ", " + R.layout.list_item_recipe); //Test output
        listView.setAdapter(doctorAdapter);
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        String jsonID = "";

        if(type.equals("getDoctors")){
            Log.d(TAG, "Interface getAsyncResult"); //Test output
            //callback result with Doctors
            //get the id from the object index 0 (API just return one object with id for Login)
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    setListViewContent(jsonArray); //set Content

                }else{
                    //no Doctor in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: No Doctor found"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }
    }
}
