package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 28/10/2016.
 */
public class EditConversionRequest extends StringRequest {
    private static final String EDITCONVERSION_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/editConversion.php";
    private Map<String, String> params;

    public EditConversionRequest(String id, String title, String conversion, Response.Listener<String> listener) {
        super(Method.POST, EDITCONVERSION_REQUEST_URL, listener, null);
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
