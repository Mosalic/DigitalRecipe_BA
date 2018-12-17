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
        String patID = getItem(position).getPatID();
        String docID = getItem(position).getDocID();
        String docTitle = getItem(position).getDocTitle();
        String docName = getItem(position).getDocName();
        String recipeID = getItem(position).getRecipeID();
        String medicine = getItem(position).getMedicine();
        String medForm = getItem(position).getMedForm();
        String medPortion = getItem(position).getMedPortion();
        String medDate = getItem(position).getMedDate();
        String isNoctu = getItem(position).getIsNoctu();
        String isAutIdem = getItem(position).getIsAutIdem();

        //Create RequireObject with the Informations
        Recipe recipe = new Recipe(patID, docID, docTitle, docName, recipeID, medicine, medForm, medPortion, medDate, isNoctu, isAutIdem);

        //??
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //get TextView from list_item_recipe.xml
        //TextView tv = (TextView) convertView.findViewById(R.id.list_item_textView1);
        TextView tvTitleDoctor = (TextView) convertView.findViewById(R.id.list_item_textView5);
        TextView tvNameDoctor = (TextView) convertView.findViewById(R.id.list_item_textView6);
        TextView tvName = (TextView) convertView.findViewById(R.id.list_item_textView2);
        TextView tvMedicine = (TextView) convertView.findViewById(R.id.list_item_textView3);
        TextView tvMedicinePortion = (TextView) convertView.findViewById(R.id.list_item_textView4);

        //set TextViews
        //tv.setText("Von: ");
        tvTitleDoctor.setText(docTitle);
        tvNameDoctor.setText(docName);
        tvName.setText("Medikament: " + medicine);
        tvMedicine.setText("Menge/Form: " + medPortion + " / " + medForm);
        tvMedicinePortion.setText("Ausstellungsdatum: " + medDate);

        return convertView;
    }
}
