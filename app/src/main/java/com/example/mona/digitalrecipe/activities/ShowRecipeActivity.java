package com.example.mona.digitalrecipe.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowRecipeActivity extends AppCompatActivity implements AsyncTaskCallback {

    private TextView tvInsurance, tvInsuranceNr, tvPatName, tvPatStreet, tvPatCity, tvOfficeName, tvOfficeNr, tvMedName, tvMedPortion, tvDocName, tvDocStreet, tvDocCity, tvDocSignature;
    private String type;
    private String userID = "";
    private Recipe recipe;
    private String recipeID = "";
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "ShowRecipeActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

        tvPatName = (TextView) findViewById(R.id.tv_recipe_name);
        tvPatStreet = (TextView) findViewById(R.id.tv_recipe_street);
        tvPatCity = (TextView) findViewById(R.id.tv_recipe_city);
        tvInsurance = (TextView) findViewById(R.id.tv_recipe_insurance);
        tvInsuranceNr = (TextView) findViewById(R.id.tv_recipe_insurance_nr);
        tvOfficeName = (TextView) findViewById(R.id.tv_recipe_office_name);
        tvOfficeNr = (TextView) findViewById(R.id.tv_recipe_office_nr);
        tvMedName = (TextView) findViewById(R.id.tv_recipe_medicine);
        tvMedPortion = (TextView) findViewById(R.id.tv_recipe_med_portion);
        tvDocName = (TextView) findViewById(R.id.tv_recipe_doc);
        tvDocStreet = (TextView) findViewById(R.id.tv_recipe_doc_street);
        tvDocCity = (TextView) findViewById(R.id.tv_recipe_doc_city);
        tvDocSignature = (TextView) findViewById(R.id.tv_recipe_doc_signature);


        //get Intent from RecipesFragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("userID");
        }
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        recipeID = recipe.getRecipeID();
        tvMedName.setText("Rezept mit ID" + recipeID);
        Log.d(TAG, "onCreate mit ID: " + recipeID); //Test output

        type = "showRecipe";
        String userrole = "Patienten";
        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID, recipeID);
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        String jsonID = "";
        //Log.d(TAG, "Interface getAsyncResult: HAAALLOOO"); //Test output
        if(type.equals("showRecipe")){
            Log.d(TAG, "Interface getAsyncResult"); //Test output
            //später wenn eine Antowrt kommt, im Moment noch buggy
            //finish();
            //callback result with infos if require is created
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty

                    Log.d(TAG, "Interface getAsyncResult: SUCCESS showRecipe response"); //Test output
                    setRecipeContent(jsonArray);

                }else{
                    //no Doctor in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: FAILED showRecipe response"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }
    }

    public void setRecipeContent(JSONArray jsonArray) {
        Log.d(TAG, "Set Content Recipe"); //Test output

        /*//get text from the userinputs
        String usersComplaint = etComplaint.getText().toString();
        String usersMedicine = etMedicine.getText().toString();
        String usersDoctor = etDoctor.getText().toString(); //id/LANR wird benötigt*/
        //String userID = "000000pati"; //id/versichertennummer wird benötigt


        for(int i = 0; i < jsonArray.length(); i++){
            try {
                //create JSON
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String patInsuranceNr = ( String) jsonObject.get("ver_nummer");
                String patInsurance = ( String) jsonObject.get("pat_insurance");
                String patLastName = ( String) jsonObject.get("pat_lastName");
                String patFirstName = ( String) jsonObject.get("pat_firstName");
                String patient = patLastName + " " + patFirstName;
                String patAdressStreet = ( String) jsonObject.get("pat_adresse_street") + " " + ( String) jsonObject.get("pat_adresse_street_nr");
                String patAdressCity = ( String) jsonObject.get("pat_adresse_PLZ") + " " + ( String) jsonObject.get("pat_adresse_city");
                String docTitle = ( String) jsonObject.get("doc_title");
                String docLANR = ( String) jsonObject.get("LANR");
                String docSignature = ( String) jsonObject.get("doc_signature");
                String docFirstName = ( String) jsonObject.get("doc_firstName");
                String docLastName = ( String) jsonObject.get("doc_lastName");
                String doctor = docTitle + " " + docFirstName + " " + docLastName;
                String office = ( String) jsonObject.get("betriebs_name");
                String officeNr = ( String) jsonObject.get("betriebs_nummer");
                String officePhone = ( String) jsonObject.get("betriebs_phone");
                String docAdressStreet = (String) jsonObject.get("doc_adresse_street") + " " + (String) jsonObject.get("doc_adresse_street_nr");
                String docAdressCity = (String) jsonObject.get("doc_adresse_PLZ") + " " + (String) jsonObject.get("doc_adresse_city");


                //Log.d(TAG, "setRecipeContent: Versicherung, Doc, Patient: " +  ", " + patInsurance +", " + doctor + ", " + patient); //Test output

                tvInsuranceNr.setText(patInsuranceNr);
                tvPatName.setText(patient);
                tvPatStreet.setText(patAdressStreet);
                tvPatCity.setText(patAdressCity);
                tvInsurance.setText(patInsurance);

                tvOfficeName.setText(office);
                tvOfficeNr.setText(officeNr);
                tvMedName.setText(recipe.getMedicine());
                tvMedPortion.setText(recipe.getMedPortion() + " " + recipe.getMedForm());
                tvDocName.setText(doctor);
                tvDocStreet.setText(docAdressStreet);
                tvDocCity.setText(docAdressCity);
                tvDocSignature.setText(docSignature);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


}
