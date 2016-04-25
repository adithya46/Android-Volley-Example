package com.adithyakatragadda.jsondatasample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView lv;
    String url = "http://hmkcode.appspot.com/rest/controller/get.json";
    JSONObject jsonObject;

    List<String> al_title = new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_main);

        StringRequest sReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           public void onResponse(String response) {

               try {
                   jsonObject = new JSONObject(response);
                   jsonObject.getJSONArray("result");
                   System.out.println("result: " + response);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
               parsingDataFromResp();
//               System.out.println("resp: " + response);
           }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });

        ForApplication.getInstance().addToRequestQueue(sReq);

    }

    public void parsingDataFromResp() {
        if(jsonObject == null || jsonObject.length() == 0) {
            System.out.println("none");
            return;
        }
        try {
            JSONArray arrayOfItems = jsonObject.getJSONArray("articleList");
            for(int i =0; i < arrayOfItems.length(); i++) {
                JSONObject currentID = arrayOfItems.getJSONObject(i);
                System.out.println("ID: " + currentID.toString());
                String title = currentID.getString("title");
                al_title.add(title);
            }
        } catch (JSONException e) {
            System.out.println("error");
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, al_title);

        lv.setAdapter(adapter);
    }

}
