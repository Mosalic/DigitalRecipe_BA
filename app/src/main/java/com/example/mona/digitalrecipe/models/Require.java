package com.example.mona.digitalrecipe.models;

import android.util.Log;

public class Require {

    private String name;
    private String medicine;
    private String complaint;
    private static final String TAG = "Require"; //TAG for test outputs

    public Require(String name, String medicine, String complaint) {
        Log.d(TAG, "Constructor"); //Test output
        this.name = name;
        this.medicine = medicine;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
