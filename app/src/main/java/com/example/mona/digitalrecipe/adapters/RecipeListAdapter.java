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
import com.example.mona.digitalrecipe.models.Recipe;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private int resource;
    private static final String TAG = "RecipeListAdapter"; //TAG for test outputs

    //Constructor
    public RecipeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Recipe> objects) {
        super(context, resource, objects);
        Log.d(TAG, "Constructor"); //Test output
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView()"); //Test output
        //get Recipe Information
        String name = getItem(position).getPatientsName();
        String medicine = getItem(position).getMedicine();
        String medicinePortion = getItem(position).getMedicinePortion();
        String nameDoctor = getItem(position).getNameDoctor();

        //Create RequireObject with the Informations
        Recipe recipe = new Recipe(name, medicine,medicinePortion, nameDoctor);

        //??
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //get TextView from list_item_recipe.xml
        TextView tvNameDoctor = (TextView) convertView.findViewById(R.id.list_item_textView1);
        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_textView2);
        TextView tvMedicine = (TextView) convertView.findViewById(R.id.list_item_textView3);
        TextView tvMedicinePortion = (TextView) convertView.findViewById(R.id.list_item_textView4);

        //set TextViews
        tvNameDoctor.setText(nameDoctor);
        tvName.setText(name);
        tvMedicine.setText(medicine);
        tvMedicinePortion.setText(medicinePortion);

        return convertView;
    }
}
