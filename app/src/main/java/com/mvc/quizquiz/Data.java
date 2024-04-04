package com.mvc.quizquiz;


import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.mvc.quizquiz.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Data {
    ArrayList<Questions> questions = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json";

    public List<Questions> getQuestions(final AsyncResponse callBack){
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray ques = jsonArray.getJSONArray(i);
                        questions.add(new Questions(ques.getString(0), ques.getBoolean(1)));

                       // Log.d("TAG", "onResponse: " + jsonArray);

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                if (null != callBack){
                    callBack.questionsGotten(questions);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("JSON Data Class", "onErrorResponse: " + volleyError.toString());

            }
        });
        Controller.getInstance().addToRequestQueue(jsonRequest);
        return questions;
    }


}
