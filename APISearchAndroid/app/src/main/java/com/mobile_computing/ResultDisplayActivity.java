package com.mobile_computing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class ResultDisplayActivity extends AppCompatActivity {
    private final String API_URL_GET_ITEM = "http://10.0.2.2:8080/api/r/books/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent display_intent = getIntent();
        int item_id = display_intent.getIntExtra("item_id", -1);
        System.out.println("Item ID for Display Activity -> " + item_id);
        display_result(item_id);
    }

    private void display_result(int item_id){
        final Context self = this;
        // Print to console on Error
        StringRequest res = new StringRequest(Request.Method.GET, API_URL_GET_ITEM + item_id,
                new Response.Listener<String>() {
                    //private ImageLoader imgLoad = VolleySingleton.getInstance(self).getImageLoader();

                    // On response
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Get the response and convert it to JSON
                            JSONObject obj = new JSONObject(response);
                            Datum datum = new Datum(obj.getInt("id"), obj.getString("title"),
                                    obj.getString("date"), obj.getString("text"), obj.getString("image").replace("http", "https"));
                            // Display the result to the screen
                            
                        } catch (Exception e) {
                            // Print to console on error
                            e.printStackTrace();
                        }
                    }
                }, Throwable::printStackTrace);
    }
}