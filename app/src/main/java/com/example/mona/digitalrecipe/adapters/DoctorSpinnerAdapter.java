package com.example.mona.digitalrecipe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.models.SpinnerDoctorItem;

import java.util.ArrayList;

public class DoctorSpinnerAdapter extends ArrayAdapter<SpinnerDoctorItem> {

    public DoctorSpinnerAdapter(Context context, ArrayList<SpinnerDoctorItem> doctorList){
        super(context, 0 , doctorList );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_spinner_item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.item_name);
        SpinnerDoctorItem currentItem = getItem(position);
        itemName.setText(currentItem.getDocName());

        return convertView;
    }
}
