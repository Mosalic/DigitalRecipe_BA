package com.example.mona.digitalrecipe.interfaces;

import org.json.JSONArray;

public interface AsyncTaskCallback {
    //Interface for getting result from Asynctask
    void getAsyncResult(JSONArray jsonArray, String type);
}
