package com.example.mona.digitalrecipe.adapters;

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
import com.example.mona.digitalrecipe.models.Require;

import java.util.ArrayList;

public class RequireListAdapter extends ArrayAdapter<Require> {

    private Context context;
    private int resource;

    private static final String TAG = "RequireListAdapter";

    //Constructor
    public RequireListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Require> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Log.d(TAG, "getView()");
        //get Require Information
        String name = getItem(position).getName();
        String medicine = getItem(position).getMedicine();
        String complaint = getItem(position).getComplaint();

        //Create RequireObject with the informations
        Require require = new Require(name, medicine, complaint);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //get TextView from list_item_require.xml
        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_textView1);
        TextView tvMedicine = (TextView) convertView.findViewById(R.id.list_item_textView2);
        TextView tvComplaint = (TextView) convertView.findViewById(R.id.list_item_textView3);

        //set TextViews
        tvName.setText("An: " + name);
        tvMedicine.setText("Medikament: " + medicine);
        tvComplaint.setText("Beschwerde: " + complaint);

        return convertView;
    }
}
