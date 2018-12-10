package com.example.mona.digitalrecipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mona.digitalrecipe.Require;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Require> {

    private Context context;
    private int resource;

    private static final String TAG = "RequireListAdapter"; //TAG for test outputs

    //Constructor
    public RecipeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Require> objects) {
        super(context, resource, objects);
        Log.d(TAG, "Constructor"); //Test output
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView()"); //Test output
        //get Require Information
        String name = getItem(position).getName();
        String medicine = getItem(position).getMedicine();
        String complaint = getItem(position).getComplaint();

        //Create RequireObject with the Informations
        Require require = new Require(name, medicine, complaint);

        //??
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //get TextView from list_item_require.xml
        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_textView1);
        TextView tvMedicine = (TextView) convertView.findViewById(R.id.list_item_textView2);
        TextView tvComplaint = (TextView) convertView.findViewById(R.id.list_item_textView3);

        //set TextViews
        tvName.setText(name);
        tvMedicine.setText(medicine);
        tvComplaint.setText(complaint);

        return convertView;
    }
}
