package com.example.mona.digitalrecipe;

public class Require {

    private String name;
    private String medicine;
    private String complaint;

    public Require(String name, String medicine, String complaint) {
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
