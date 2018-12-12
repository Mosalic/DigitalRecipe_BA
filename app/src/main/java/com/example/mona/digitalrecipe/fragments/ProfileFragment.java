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

import org.json.JSONArray;

public class ProfileFragment extends Fragment implements AsyncTaskCallback {

    View view;
    private TextView tvUsername;
    private TextView tvInsurance;
    private TextView tvInsuranceNr;
    private TextView tvGeb;
    private TextView tvName;
    private TextView tvStreet;
    private TextView tvCity;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "ProfileFragment"; //TAG for test outputs

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView"); //Test output
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tv_profile_username);
        tvInsurance = view.findViewById(R.id.tv_profile_insurance);
        tvInsuranceNr = view.findViewById(R.id.tv_profile_insuranceNumber);
        tvGeb = view.findViewById(R.id.tv_profile_geb);
        tvName = view.findViewById(R.id.tv_profile_name);
        tvStreet = view.findViewById(R.id.tv_profile_street);
        tvCity = view.findViewById(R.id.tv_profile_city);

        tvUsername.setText("Username");
        tvInsurance.setText("Versicherung");
        tvInsuranceNr.setText("D00000001");
        tvGeb.setText("05.03.1964");
        tvName.setText("Dorothea Schmidt");
        tvStreet.setText("Friedhofsweg 7");
        tvCity.setText("20537 Hamburg");


       /* //get transfered parameter from HomeActivity
        String userID = "";
        if(getArguments() != null){
            userID = getArguments().getString("id");
            // ArrayList<String> jsonList = getArguments().getStringArrayList("requireArray");
            Log.d(TAG, "onCreateView get Arguments: " + userID ); //Test output
            //setListViewContent(jsonList);
        }

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        String type = "getPatients";
        String userrole = "Patienten";
        backgroundHandler.execute(type, userrole, userID);*/

        return view;
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {

    }
}
