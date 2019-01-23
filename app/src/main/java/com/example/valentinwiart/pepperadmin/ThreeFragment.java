package com.example.valentinwiart.pepperadmin;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
    public TextView textToSpeech;
    private int num_movement = 10000;
    private Button mButton;
    private Button mButton2;

    private Button mButon_Reset;
    private Button mButton_LoadGame;

    private int nbr_questions = 2;
    private Spinner spinner;
    private static final String[] paths = {"1 question", "2 questions", "3 questions","4 questions","5 questions","6 questions","7 questions"};


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


        View view = inflater.inflate(R.layout.fragment_three, container, false);

        textToSpeech = view.findViewById(R.id.xml_textToSpeech);

        mButton = view.findViewById(R.id.xml_button_send_textToSpeech);
        mButton.setOnClickListener(this);

        mButton2 = view.findViewById(R.id.xml_buton_startgame);
        mButton2.setOnClickListener(this);


        mButon_Reset = view.findViewById(R.id.xml_buton_reset);
        mButon_Reset.setOnClickListener(this);



        mButton_LoadGame= view.findViewById(R.id.xml_buton_loadGame);
        mButton_LoadGame.setOnClickListener(this);

        spinner = (Spinner)view.findViewById(R.id.xml_spinner_1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                nbr_questions = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nbr_questions = 2;
            }
        });

        return view;
    }

    public void SendAction(String URL, int RequestMethod, int id_Movement, String Content, String Name, int Num_movement){

        final int pid_Movement  = id_Movement;
        final String pContent   = Content;
        final String pName      = Name;
        final int pNum_movement = Num_movement;

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
                params.put("id_movement", String.valueOf(pid_Movement));
                params.put("content", pContent);
                params.put("name", pName);
                params.put("num_movement", String.valueOf(pNum_movement));
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.xml_button_send_textToSpeech:
                Toast.makeText(view.getContext(), "Button SendText Click :" +textToSpeech.getText().toString() , Toast.LENGTH_SHORT).show();
                if (textToSpeech.getText().toString() == "")textToSpeech.setText("Bijour a touss");
                num_movement = num_movement + 1 ;
                SendAction(production + "movement", Request.Method.POST,100,textToSpeech.getText().toString(),"TextToSpeech: "+textToSpeech.getText().toString(),num_movement);
                break;

            case R.id.xml_buton_startgame:
                Toast.makeText(view.getContext(), "Button Start Click" , Toast.LENGTH_SHORT).show();
                num_movement = num_movement + 1 ;
                SendAction(production + "StartGameTimer", Request.Method.POST,999,String.valueOf(nbr_questions),"Start Game",num_movement);
                break;

            case R.id.xml_buton_loadGame:
                Toast.makeText(view.getContext(), "Button LoadGame Click" , Toast.LENGTH_SHORT).show();
                num_movement = num_movement + 1 ;
                SendAction(production + "movement", Request.Method.POST,103,"","LoadGame",num_movement);
                break;

            case R.id.xml_buton_reset:
                Toast.makeText(view.getContext(), "Button Reset Click" , Toast.LENGTH_SHORT).show();
                num_movement = num_movement + 1 ;
                SendAction(develop + "ResetGame", Request.Method.POST,9999,"","Reset Game",num_movement);
                break;





            default:


        }
    }
}