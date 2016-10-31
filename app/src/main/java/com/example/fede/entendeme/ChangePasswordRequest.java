package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 31/10/2016.
 */
public class ChangePasswordRequest extends StringRequest {
    private static final String CHANGEMAIL_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/changePassword.php";
    private Map<String, String> params;

    public ChangePasswordRequest(String id, String password, Response.Listener<String> listener) {
        super(Method.POST, CHANGEMAIL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
