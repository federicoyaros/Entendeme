package com.example.fede.entendeme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 28/10/2016.
 */
public class ChangeMailRequest extends StringRequest {
    private static final String CHANGEMAIL_REQUEST_URL = "http://entendemedb.esy.es/entendemeConnect/changeMail.php";
    private Map<String, String> params;

    public ChangeMailRequest(String id, String mail, Response.Listener<String> listener) {
        super(Method.POST, CHANGEMAIL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("mail", mail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
