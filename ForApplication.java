package com.adithyakatragadda.jsondatasample;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

/**
 * Created by adithyakatragadda on 4/25/16.
 */
public class ForApplication extends Application {

    private static ForApplication mForApp;
    private static RequestQueue mRequestQueue;
    private static String TAG = "DEFAULT";

    public void onCreate(){
        super.onCreate();

        mForApp = this;
    }

    public static synchronized ForApplication getInstance() {
        return mForApp;
    }

    public static RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mForApp);
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public  void  cancelPendingRequest(Object tag) {
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

}
