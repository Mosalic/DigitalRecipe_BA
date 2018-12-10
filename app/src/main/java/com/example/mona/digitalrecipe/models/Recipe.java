package com.example.mona.digitalrecipe.models;

import android.util.Log;

public class Recipe {

    private String namePatient;
    private String medicine;
    private String medicinePortion;
    private String nameDoctor;
    private static final String TAG = "Recipe"; //TAG for test outputs

    public Recipe(String namePatient, String medicine, String medicinePortion, String nameDoctor) {
        Log.d(TAG, "Constructor"); //Test output
        this.namePatient = namePatient;
        this.medicine = medicine;
        this.medicinePortion = medicinePortion;
        this.nameDoctor = nameDoctor;
    }

    public String getPatientsName() {
        return namePatient;
    }

    public void setPatientsName(String name) {
        this.namePatient = name;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getMedicinePortion() {
        return medicinePortion;
    }

    public void setMedicinePortion(String medicinePortion) {
        this.medicinePortion = medicinePortion;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

}
