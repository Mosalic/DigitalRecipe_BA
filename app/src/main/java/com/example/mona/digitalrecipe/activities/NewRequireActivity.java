package com.example.mona.digitalrecipe.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mona.digitalrecipe.R;
import com.example.mona.digitalrecipe.adapters.DoctorSpinnerAdapter;
import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;
import com.example.mona.digitalrecipe.models.SpinnerDoctorItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewRequireActivity extends AppCompatActivity implements AsyncTaskCallback {

    private EditText etComplaint, etMedicine;
    private String type;
    private String userID = "";
    private String usersDoctor = "";
    private Spinner docSpinner;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "NewRequireActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mona.digitalrecipe.R.layout.activity_new_require);

        etComplaint = (EditText) findViewById(R.id.et_complaint);
        etMedicine = (EditText) findViewById(R.id.et_medicine);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("userID");
        }

        createDocSpinner();
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        String jsonID = "";

        if(type.equals("createNewRequire")){
            finish();
            //callback result with infos if require is created
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    String info = (String) jsonObject.get("info");
                    if(type.equals(info)){
                        //Log.d(TAG, "Interface getAsyncResult: SUCCESS Require created");
                        Toast.makeText(this, "Anforderung erstellt!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //no Doctor in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: FAILED Require created");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(type.equals("getDoctors")){
            //callback result
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0); //get first object

                //check if jsonobject is epmty or not
                if(jsonObject.length() > 0){
                    //not epmty
                    setSpinnerContent(jsonArray);
                }else{
                    //no Doctor in database, show empty Fragment
                    Log.d(TAG, "Interface getAsyncResult: FAILED Require created"); //Test output
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        backgroundHandler.cancel(true);
    }



    public void createNewRequire(View view) {

        if(usersDoctor.equals("")){
            Toast.makeText(NewRequireActivity.this, "Bitte wähle einen Arzt", Toast.LENGTH_SHORT).show();
        }else{
            //get text from the userinputs
            String usersComplaint = etComplaint.getText().toString();
            String usersMedicine = etMedicine.getText().toString();

            type = "createNewRequire";
            String userrole = "Patienten";

            //create instance of BackgroundWorker Class
            backgroundHandler = new BackgroundHandler(this);
            backgroundHandler.execute(type, userrole, userID, usersComplaint, usersMedicine, usersDoctor);
        }
    }

    public void createDocSpinner() {
        docSpinner = (Spinner) findViewById(R.id.spinner);

        //add Listener
        docSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //set docs LANR for the require
                SpinnerDoctorItem selectedItem = (SpinnerDoctorItem) adapterView.getItemAtPosition(i);
                usersDoctor = selectedItem.getDocLANR();
                Log.d(TAG, "onItemSelected: setLANR: " + usersDoctor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //no LANR, no require
            }
        });

        type = "getDoctors";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, userID);
    }

    private void setSpinnerContent(JSONArray jsonArray) {
        //initialise arraylist and add requires
        ArrayList<SpinnerDoctorItem> doctorList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                //create JSON
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //create and set Adress for Doc
                String fkLANR = (String) jsonObject.get("LANR_fk");
                String docLastName = (String) jsonObject.get("doc_lastName");
                String docFirstName = (String) jsonObject.get("doc_firstName");
                String docTitle = (String) jsonObject.get("doc_title");
                String docName = docTitle + " " + docFirstName + " " + docLastName;

                //create and add SpinnerItem for each Doctor
                SpinnerDoctorItem spinnerItem = new SpinnerDoctorItem(fkLANR, docName);
                doctorList.add(spinnerItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Adapter declaration
        DoctorSpinnerAdapter spinnerAdapter = new DoctorSpinnerAdapter(this, doctorList);
        docSpinner.setAdapter(spinnerAdapter);
    }
}
