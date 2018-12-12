package com.example.mona.digitalrecipe.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewRequireActivity extends AppCompatActivity implements AsyncTaskCallback {

    private EditText etDoctor, etComplaint, etMedicine;
    private String type;
    private String userID = "";
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "NewRequireActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mona.digitalrecipe.R.layout.activity_new_require);

        etDoctor = (EditText) findViewById(R.id.et_doctor);
        etComplaint = (EditText) findViewById(R.id.et_complaint);
        etMedicine = (EditText) findViewById(R.id.et_medicine);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("userID");
        }

        Log.d(TAG, "onCreate mit ID: " + userID); //Test output
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

        //get text from the userinputs
        String usersComplaint = etComplaint.getText().toString();
        String usersMedicine = etMedicine.getText().toString();
        String usersDoctor = etDoctor.getText().toString(); //id/LANR wird benötigt
        //String userID = "000000pati"; //id/versichertennummer wird benötigt
        type = "createNewRequire";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID, usersComplaint, usersMedicine, usersDoctor);
    }


}
