package com.example.valentinwiart.pepperadmin;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

    public class CustomRequest extends JsonRequest<JSONArray> {

        /**
         * Creates a new request.
         * @param method the HTTP method to use
         * @param url URL to fetch the JSON from
         * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
         *   indicates no parameters will be posted along with request.
         * @param listener Listener to receive the JSON response
         * @param errorListener Error listener, or null to ignore errors.
         */
        public CustomRequest(int method, String url, JSONObject jsonRequest,
                                      Listener<JSONArray> listener, ErrorListener errorListener) {
            super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                    errorListener);
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                return Response.success(new JSONArray(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }