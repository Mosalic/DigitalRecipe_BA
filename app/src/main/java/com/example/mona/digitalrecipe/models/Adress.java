package com.example.mona.digitalrecipe.models;

import android.util.Log;

import java.util.ArrayList;

public class Adress {

    private String idAdress;
    private String street;
    private String streetNr;
    private String plz;
    private String city;
    private static final String TAG = "Adress"; //TAG for test outputs

    //Constructor
    public Adress(String idAdress, String street, String streetNr, String plz, String city) {
        Log.d(TAG, "Constructor"); //Test output
        this.idAdress = idAdress;
        this.street = street;
        this.streetNr = streetNr;
        this.plz = plz;
        this.city = city;
    }

    //Getter and Setter
    public String getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(String id_adress) {
        this.idAdress = idAdress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
