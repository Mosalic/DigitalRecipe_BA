package com.example.mona.digitalrecipe.models;

import android.util.Log;

import java.util.ArrayList;

public class Doctor {

    private String office;
    private String phoneNumber;
    private ArrayList<String> adress;
    private String title;
    private String firstName;
    private String lastName;
    private static final String TAG = "Doctor"; //TAG for test outputs

    //Constructor
    public Doctor(String firstName, String lastName, String title, String office, String phoneNumber, ArrayList<String> adress) {
        Log.d(TAG, "Constructor"); //Test output
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.office = office;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
    }

    //Getter and Setter
    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<String> getAdress() {
        return adress;
    }

    public void setAdress(ArrayList<String> adress) {
        this.adress = adress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
