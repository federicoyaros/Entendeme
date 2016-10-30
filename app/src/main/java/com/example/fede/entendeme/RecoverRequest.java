package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 29/10/2016.
 */
public class RecoverRequest extends StringRequest {

    private static final String CONVERSION_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/recoverPassword.php";

    private Map<String, String> params;

    public RecoverRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, CONVERSION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
