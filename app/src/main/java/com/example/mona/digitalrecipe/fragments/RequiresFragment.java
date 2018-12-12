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
import android.widget.Button;
import android.widget.ListView;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.activities.NewRequireActivity;
import com.example.mona.digitalrecipe.models.Require;
import com.example.mona.digitalrecipe.adapters.RequireListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequiresFragment extends Fragment{

    View view;
    private ListView listView;
    private Context context;
    private static final String TAG = "RequiresFragment"; //TAG for test outputs


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_requires, container, false);

        listView = view.findViewById(R.id.requireListView);
        Button btnCreateRequire = (Button) view.findViewById(R.id.button_createRequire);
        context = container.getContext();

        Log.d(TAG, "onCreate" ); //Test output

        //get transfered parameter from HomeActivity
        String userID = "";
        if(getArguments() != null){
            userID = getArguments().getString("id");
            ArrayList<String> jsonList = getArguments().getStringArrayList("requireArray");
            Log.d(TAG, "onCreateView get Arguments: " + userID + ", " + jsonList); //Test output
            setListViewContent(jsonList);
        }

        //set Listener for Button
        final String user_ID = userID; //muss final sein damit es im Listener verwendet werden kann?
        btnCreateRequire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewRequireActivity.class);
                intent.putExtra("userID", user_ID);
                startActivity(intent);
            }
        });

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

    private void setListViewContent(ArrayList<String> arrayList){
        //initialise arraylist and add requires
        ArrayList<Require> requireList = new ArrayList<>();
        Log.d(TAG, "setListViewContent "); //Test output

        for(int i = 0; i < arrayList.size(); i++){
            try {
                //create JSON
                JSONObject jsonObject = new JSONObject(arrayList.get(i));

                String user = ( String) jsonObject.get("ver_nummer");
                String complaint = ( String) jsonObject.get("beschwerden");
                String medicine = ( String) jsonObject.get("med_name");
                Log.d(TAG, "setListViewContent: user, complaint, medicine: " + user + ", " + complaint +", " + medicine); //Test output

                Require require = new Require(user, medicine, complaint);
                requireList.add(require);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

            //jsonIsUser =  Boolean.valueOf(jsonArray.getJSONObject(0).getString("isUser"));
            //Log.d(TAG, "setListViewContent: " + jsonList.get(i)); //Test output

        //Test requires
          /*Require require1 = new Require("Mona", "Patropazol", "Magenschmerzen");
            Require require2 = new Require("Lisa", "Aspirin", "Kopfschmerzen");
            Require require3 = new Require("Doro", "Schnaps", "Verdauung");
            Require require4 = new Require("Norbert", "Schlaf", "Migräne");
            Require require5 = new Require("Mona", "Patropazol", "Magenschmerzen");
            Require require6 = new Require("Lisa", "Aspirin", "Kopfschmerzen");
            Require require7 = new Require("Doro", "Schnaps", "Verdauung");
            Require require8 = new Require("Norbert", "Schlaf", "Migräne");


         requireList.add(require1);
            requireList.add(require2);
            requireList.add(require3);
            requireList.add(require4);
            requireList.add(require5);
            requireList.add(require6);
            requireList.add(require7);
            requireList.add(require8);*/


        //Adapter declaration
        RequireListAdapter requireAdapter = new RequireListAdapter(context, R.layout.list_item_require, requireList);
        listView.setAdapter(requireAdapter);
    }
}
