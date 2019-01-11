package com.example.valentinwiart.pepperadmin;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class ThreeFragment extends Fragment implements View.OnClickListener{

    public String production = "https://pepper-prod.herokuapp.com/";
    public String staging = "https://pepper-staging-2.herokuapp.com/";
    public String develop = "https://pepper-stagging.herokuapp.com/";
    public boolean production_status = false;
    public boolean staging_status = false;
    public boolean develop_status = false;
    public TextView xml_production_status ;
    public TextView xml_staging_status ;
    public TextView xml_develop_status ;
    private Button mButton;
    private Button mButton2;

    public ThreeFragment() {
        // Required empty public constructor
    }

    public boolean pingURL(String url, int timeout) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }

        //             boolean status;
        //            status = pingURL("https://pepper-prod.herokuapp.com/",200);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    production_status   = pingURL(production,200);
                    staging_status      = pingURL(staging,200);
                    develop_status      = pingURL(develop,200);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


        View view = inflater.inflate(R.layout.fragment_three, container, false);
        xml_production_status = view.findViewById(R.id.xml_production_status);
        xml_develop_status = view.findViewById(R.id.xml_develop_status);
        xml_staging_status = view.findViewById(R.id.xml_staging_status);
        xml_production_status.setText(production);
        xml_develop_status.setText(develop);
        xml_staging_status.setText(staging);

        if(production_status){
            xml_production_status.setTextColor(R.color.green);
            Log.i("changement couleur","vert");
        }
        xml_staging_status.setTextColor(R.color.green);
        xml_develop_status.setTextColor(R.color.green);
        mButton = view.findViewById(R.id.xml_button_send);
        mButton.setOnClickListener(this);

        mButton2 = view.findViewById(R.id.xml_buton_startgame);
        mButton2.setOnClickListener(this);
        return view;
    }

    public void SendAction(String URL, int RequestMethod){


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // 0 GET et 1 POST
        StringRequest stringRequest = new StringRequest(RequestMethod, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_movement", "5");
                params.put("name", "name");
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.xml_buton_startgame:
                Toast.makeText(view.getContext(), "Button Start Click" , Toast.LENGTH_SHORT).show();
                SendAction(staging + "StartGameTimer", Request.Method.GET);
                break;


            case R.id.xml_button_send:
                Toast.makeText(view.getContext(), "Button Send Click" , Toast.LENGTH_SHORT).show();
                SendAction(staging + "movement", Request.Method.POST);
                break;


            default:


        }
    }
}