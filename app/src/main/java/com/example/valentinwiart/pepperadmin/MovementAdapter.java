package com.example.valentinwiart.pepperadmin;
import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.ViewHolder> {

    private Context context;
    private List<Movement> list;
    private int num_movement = 0;
    private String url = "https://pepper-prod.herokuapp.com/";

    public MovementAdapter(Context context, List<Movement> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        Movement movement = list.get(position);

        holder.textTitle.setText(movement.getName());
        holder.textDescription.setText(movement.getDescription());
        holder.firstletter.setText(movement.getName().substring(0, 1));
        holder.firstletter.setBackgroundColor(randomAndroidColor);
        //holder.textIndex.setText(String.valueOf(movement.getIndex()));

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        public TextView textTitle, textDescription, textIndex,firstletter;




        public ViewHolder(View itemView) {
            super(itemView);
            firstletter = itemView.findViewById(R.id.xml_title_first_letter);
            textTitle = itemView.findViewById(R.id.xml_title);
            textDescription = itemView.findViewById(R.id.xml_description);
            itemView.setOnClickListener(this);

            // textIndex = itemView.findViewById(R.id.xml_index);
        }


        public void SendAction(String URL, int RequestMethod, String movement_id, String name, int num_movement){
            final int pnum_movement = num_movement;
            final String pmovement = movement_id;
            final String pname = name;
            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());

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
                    params.put("id_movement", pmovement);
                    params.put("content", "");
                    params.put("name", pname);
                    params.put("num_movement", String.valueOf(pnum_movement));
                    return params;
                }

            };
            requestQueue.add(stringRequest);

        }
        @Override
        public void onClick(View view) {

            Toast.makeText(view.getContext(), "Click on position = " + getLayoutPosition() , Toast.LENGTH_SHORT).show();
            num_movement = num_movement + 1 ;
            Log.i("String00",String.valueOf(num_movement));

            Movement movement = list.get(getLayoutPosition());
            SendAction(url +"movement", Request.Method.POST, Integer.toString(getLayoutPosition()), movement.getName(),num_movement);

        }

    }

}
