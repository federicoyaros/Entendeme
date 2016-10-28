package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 28/10/2016.
 */
public class DeleteConversionRequest extends StringRequest {
    private static final String DELETECONVERSION_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/deleteConversion.php";
    private Map<String, String> params;

    public DeleteConversionRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, DELETECONVERSION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

