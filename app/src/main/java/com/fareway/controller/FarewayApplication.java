package com.fareway.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static android.content.ContentValues.TAG;
public class FarewayApplication {

    private static FarewayApplication mInstance;
    private RequestQueue mRequestQueue;

    private FarewayApplication(Context context){
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized FarewayApplication getmInstance(Context context){
        if (mInstance==null){
            mInstance = new FarewayApplication(context);
        }
        return mInstance;
    }
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }


}


