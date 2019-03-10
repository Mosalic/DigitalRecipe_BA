package com.example.mona.digitalrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.models.Adress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment implements AsyncTaskCallback {

    View view;
    private TextView tvUsername;
    private TextView tvInsurance;
    private TextView tvInsuranceNr;
    private TextView tvGeb;
    private TextView tvName;
    private TextView tvStreet;
    private TextView tvCity;
    private String userID = "";
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tv_profile_username);
        tvInsurance = view.findViewById(R.id.tv_profile_insurance);
        tvInsuranceNr = view.findViewById(R.id.tv_profile_insuranceNumber);
        tvGeb = view.findViewById(R.id.tv_profile_geb);
        tvName = view.findViewById(R.id.tv_profile_name);
        tvStreet = view.findViewById(R.id.tv_profile_street);
        tvCity = view.findViewById(R.id.tv_profile_city);


       //get transfered parameter from HomeActivity
        if(getArguments() != null){
            userID = getArguments().getString("id");
            //Log.d(TAG, "onCreateView get Arguments: " + userID ); //Test output
            //setListViewContent(jsonList);
        }

        String type = "getPatients";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID);

        return view;
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        String jsonID = "";

        if(type.equals("getPatients")){
            //callback result with Patients data
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    //create and set Adress for Doc
                    String idAdress = (String) jsonObject.get("id_adress");
                    String adressStreet = (String) jsonObject.get("adress_street");
                    String  adressStreetNr = (String) jsonObject.get("adress_street_nr");
                    String adressPLZ = (String) jsonObject.get("adress_PLZ");
                    String adressCity = (String) jsonObject.get("adress_city");

                    //set other doc infos
                    String insurance = (String) jsonObject.get("user_ver");
                    String userGeb = (String) jsonObject.get("user_geb");
                    String firstName = (String) jsonObject.get("user_firstName");
                    String lastName = (String) jsonObject.get("user_lastName");
                    String userName = (String) jsonObject.get("user_username");

                    //set Content
                    tvUsername.setText("User: " + userName);
                    tvInsurance.setText(insurance);
                    tvInsuranceNr.setText(userID);
                    tvGeb.setText(userGeb);
                    tvName.setText(firstName + " " + lastName);
                    tvStreet.setText(adressStreet + " " + adressStreetNr);
                    tvCity.setText(adressPLZ + " " + adressCity);

                }else{
                    //no Patient in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: No Patient found");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }
    }
}
