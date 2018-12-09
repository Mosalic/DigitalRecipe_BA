package com.example.mona.digitalrecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mona.digitalrecipe.Interfaces.AsyncTaskCallback;

import org.json.JSONArray;

import java.util.ArrayList;

public class RequiresFragment extends Fragment implements AsyncTaskCallback {

    View view;
    private ListView listView;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "RequiresFragment"; //TAG for test outputs

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_requires, container, false);
        Log.d(TAG, "onCreateView"); //Test output

        listView = view.findViewById(R.id.listView);

        //create instance of BackgroundWorker Class
        String username = "testname";
        String password = "testpasswort";
        String type = "getRequires"; //ändern zu home oder getRequires
        String userrole = "Patienten";
        //backgroundHandler = new BackgroundHandler(this );
        //backgroundHandler.execute(type, userrole, username, password);

        //Create test Requires for now, later get them from the database
        Require require1 = new Require("Mona", "Patropazol", "Magenschmerzen");
        Require require2 = new Require("Lisa", "Aspirin", "Kopfschmerzen");
        Require require3 = new Require("Doro", "Schnaps", "Verdauung");
        Require require4 = new Require("Norbert", "Schlaf", "Migräne");
        Require require5 = new Require("Mona", "Patropazol", "Magenschmerzen");
        Require require6 = new Require("Lisa", "Aspirin", "Kopfschmerzen");
        Require require7 = new Require("Doro", "Schnaps", "Verdauung");
        Require require8 = new Require("Norbert", "Schlaf", "Migräne");

        //initialise arraylist and add requires
        ArrayList<Require> requireList = new ArrayList<>();
        requireList.add(require1);
        requireList.add(require2);
        requireList.add(require3);
        requireList.add(require4);
        requireList.add(require5);
        requireList.add(require6);
        requireList.add(require7);
        requireList.add(require8);

        //Adapter declaration
        RequireListAdapter requireAdapter = new RequireListAdapter(container.getContext(), R.layout.list_item_require, requireList);
        listView.setAdapter(requireAdapter);

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

    @Override
    public void getAsyncResult(JSONArray jsonArray) {

    }
}
