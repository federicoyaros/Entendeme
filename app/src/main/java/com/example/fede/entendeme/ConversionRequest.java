package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ConversionRequest extends StringRequest {

    private static final String CONVERSION_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/getConversion.php";

    private Map<String, String> params;

    public ConversionRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, CONVERSION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        //params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
