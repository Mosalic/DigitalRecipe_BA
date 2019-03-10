package com.example.mona.digitalrecipe.models;

import android.util.Log;

import java.io.Serializable;

public class Recipe implements Serializable {


    private String patID;
    private String docID;
    private String docTitle;
    private String docName;
    private String recipeID;
    private String medicine;
    private String medForm;
    private String medPortion;
    private String medDate;
    private String isNoctu;
    private String isAutIdem;

    private static final String TAG = "Recipe";

    //Constructor
    public Recipe(String patID, String docID, String docTitle, String docName, String recipeID, String medicine, String medForm, String medPortion, String medDate, String noctu, String autIdem) {
        //Log.d(TAG, "Constructor");

        this.patID = patID;
        this.docID = docID;
        this.docTitle = docTitle;
        this.docName = docName;
        this.recipeID = recipeID;
        this.medicine = medicine;
        this.medForm = medForm;
        this.medPortion = medPortion;
        this.medDate = medDate;
        this.isNoctu = noctu;
        this.isAutIdem = autIdem;
    }


    //Getter and Setter
    public String getPatID() {
        return patID;
    }

    public void setPatID(String patID) {
        this.patID = patID;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getMedForm() {
        return medForm;
    }

    public void setMedForm(String medForm) {
        this.medForm = medForm;
    }

    public String getMedPortion() {
        return medPortion;
    }

    public void setMedPortion(String medPortion) {
        this.medPortion = medPortion;
    }

    public String getMedDate() {
        return medDate;
    }

    public void setMedDate(String medDate) {
        this.medDate = medDate;
    }

    public String getIsNoctu() {
        return isNoctu;
    }

    public void setIsNoctu(String isNoctu) {
        this.isNoctu = isNoctu;
    }

    public String getIsAutIdem() {
        return isAutIdem;
    }

    public void setIsAutIdem(String isAutIdem) {
        this.isAutIdem = isAutIdem;
    }



}
