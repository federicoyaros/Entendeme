package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnForgotPassword, btnRegister;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Debe completar todos los datos")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //ProgressDialog pd=new ProgressDialog(LoginActivity.this);
                                //pd.setTitle("Procesando");
                                //pd.setMessage("Buscando datos...Por favor espere");
                                //pd.show();
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                int id = Integer.parseInt(jsonResponse.getString("id"));

                                if (success) {
                                    Entendeme app = ((Entendeme)getApplicationContext());
                                    app.setUsuario(etUsername.getText().toString());
                                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                                    i.putExtra("id", id);
                                    pd.dismiss();
                                    startActivity(i);
                                } else {
                                    pd.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Datos incorrectos")
                                            .setNegativeButton("Volver", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    pd=new ProgressDialog(LoginActivity.this);
                    pd.setTitle("Procesando");
                    pd.setMessage("Buscando datos...Por favor espere");
                    pd.show();
                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });
    }

    public void onClickRegister (View view)
    {
        Intent i = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void onClickRecover (View view)
    {
        Intent i = new Intent(getBaseContext(), RecoverActivity.class);
        startActivity(i);
    }
}

