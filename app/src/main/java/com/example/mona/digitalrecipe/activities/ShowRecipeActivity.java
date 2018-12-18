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

    private TextView tvMedName, etComplaint, etMedicine;
    private String type;
    private String recipeID = "";
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "ShowRecipeActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

        tvMedName = (TextView) findViewById(R.id.tv_medName);
        /*etComplaint = (TextView) findViewById(R.id.et_complaint);
        etMedicine = (TextView) findViewById(R.id.et_medicine);*/


        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            recipeID = bundle.getString("recipeID");
        }*/

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        recipeID = recipe.getRecipeID();

        tvMedName.setText("Rezept mit ID" + recipeID);
        Log.d(TAG, "onCreate mit ID: " + recipeID); //Test output
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        String jsonID = "";

        if(type.equals("createNewRequire")){
            Log.d(TAG, "Interface getAsyncResult"); //Test output
            //später wenn eine Antowrt kommt, im Moment noch buggy
            finish();
            //callback result with infos if require is created
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    String info = (String) jsonObject.get("info");
                    if(type.equals(info)){
                        Log.d(TAG, "Interface getAsyncResult: SUCCESS Require created"); //Test output
                        //Toast kurz als Rückgabe anzeigen und Activity schließen und HomaActivity wieder anzeigen
                        Toast.makeText(this, "Anforderung erstellt!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //no Doctor in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: FAILED Require created"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundHandler.cancel(true);
        }
    }

    public void createNewRequire(View view) {
        Log.d(TAG, "Button click: create new Require"); //Test output

        /*//get text from the userinputs
        String usersComplaint = etComplaint.getText().toString();
        String usersMedicine = etMedicine.getText().toString();
        String usersDoctor = etDoctor.getText().toString(); //id/LANR wird benötigt
        //String userID = "000000pati"; //id/versichertennummer wird benötigt
        type = "createNewRequire";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID, usersComplaint, usersMedicine, usersDoctor);*/
    }


}
