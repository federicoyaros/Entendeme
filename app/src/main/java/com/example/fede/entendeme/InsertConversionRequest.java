package com.example.fede.entendeme;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 2/11/2016.
 */
public class InsertConversionRequest extends StringRequest {
    private static final String INSERTCONVERSION_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/insertConversion.php";
    private Map<String, String> params;

    public InsertConversionRequest(String id, String title, String conversion, Response.Listener<String> listener) {
        super(Method.POST, INSERTCONVERSION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("title", title);
        params.put("conversion", conversion);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
