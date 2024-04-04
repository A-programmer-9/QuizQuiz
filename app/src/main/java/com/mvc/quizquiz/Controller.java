package com.mvc.quizquiz;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Controller extends Application {
    public static Controller instance;
    public RequestQueue queue;

    public static synchronized Controller getInstance(){
        return instance;
    }

    public RequestQueue getQueue() {
        if (queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        return queue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getQueue().add(req);

    }
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

}
