package com.example.mona.digitalrecipe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.models.Adress;
import com.example.mona.digitalrecipe.models.Doctor;
import com.example.mona.digitalrecipe.models.Recipe;

import java.util.ArrayList;

public class DoctorListAdapter extends ArrayAdapter<Doctor> {

    private Context context;
    private int resource;
    private static final String TAG = "DoctorListAdapter"; //TAG for test outputs

    //Constructor
    public DoctorListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Doctor> objects) {
        super(context, resource, objects);
        Log.d(TAG, "Constructor"); //Test output
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView()"); //Test output
        //get Doctor Information
        String office = getItem(position).getOffice();
        String phoneNumber = getItem(position).getPhoneNumber();
        Adress adress = getItem(position).getAdress();
        String title = getItem(position).getTitle();
        String firstName = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();

        //Create RequireObject with the Informations
        Doctor doctor = new Doctor(firstName, lastName, title, office, phoneNumber, adress);

        //??
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //get TextView from list_item_doctor.xml
        TextView tvNameDoctor = (TextView) convertView.findViewById(R.id.list_item_textView1);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.list_item_textView2);
        TextView tvOffice = (TextView) convertView.findViewById(R.id.list_item_textView3);
        TextView tvStreet = (TextView) convertView.findViewById(R.id.list_item_textView4);
        TextView tvCity = (TextView) convertView.findViewById(R.id.list_item_textView5);

        //set TextViews
        tvNameDoctor.setText(title + " " + firstName + " " + lastName);
        tvPhone.setText(phoneNumber);
        tvOffice.setText(office);
        tvStreet.setText(adress.getStreet() + " " + adress.getStreetNr());
        tvCity.setText(adress.getPlz() + " " + adress.getCity());

        return convertView;
    }
}
