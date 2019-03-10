package com.example.mona.digitalrecipe.models;

import android.util.Log;

public class SpinnerDoctorItem {

    private String docName;
    private String docLANR;
    private static final String TAG = "Adress";

    public SpinnerDoctorItem(String LANR, String name){
        //Log.d(TAG, "Constructor");
        this.docLANR = LANR;
        this.docName = name;
    }

    //Getter Methods
    public String getDocName() {
        return docName;
    }

    public String getDocLANR() {
        return docLANR;
    }
}
