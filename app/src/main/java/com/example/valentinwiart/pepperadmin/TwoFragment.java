package com.example.valentinwiart.pepperadmin;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TwoFragment extends Fragment {
    private RecyclerView mList;
    private StringRequest request;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private ArrayList<Movement> movementList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private static final String TAG = "JSON";


    private String url = "https://pepper-staging-2.herokuapp.com/json";


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = jsonObject.getJSONObject("something");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonObject2.optJSONArray("questions");

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Iterate the jsonArray and print the info of JSONObjects
                        try {
                            JSONObject currently = jsonArray.getJSONObject(i);

                            Movement movement = new Movement();
                            movement.setName(currently.getString("name"));
                            movement.setDescription(currently.getString("description"));
                            movement.setIndex(currently.getInt("index"));

                            movementList.add(movement);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }
                    }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mList = view.findViewById(R.id.main_list);
        getData();
        adapter = new MovementAdapter( getActivity(), movementList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);


        return  view;
    }

}