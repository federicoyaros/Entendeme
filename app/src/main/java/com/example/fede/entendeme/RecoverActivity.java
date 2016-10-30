package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

/**
 * Created by fede on 31/8/2016.
 */
public class RecoverActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnRecover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);

        etUsername = (EditText) findViewById(R.id.etUsername);
        btnRecover = (Button) findViewById(R.id.btnRecover);

        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = etUsername.getText().toString();
                if (username.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecoverActivity.this);
                    builder.setMessage("Debe completar los datos")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();

                }else{

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                boolean invalidUser = jsonResponse.getBoolean("invalidUser");
                                String mail = jsonResponse.getString("mail");
                                String password = jsonResponse.getString("password");
                                if(invalidUser)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RecoverActivity.this);
                                    builder.setMessage("No existe un usuario con los datos ingresados")
                                            .setNegativeButton("Volver", null)
                                            .create()
                                            .show();
                                }
                                else{
                                    if (success)
                                    {
                                        Gmail androidEmail = new Gmail();
                                        androidEmail.Gmail(mail, password);

                                        try {
                                            androidEmail.createEmailMessage();

                                            androidEmail.sendEmail(RecoverActivity.this);
                                            Toast.makeText(RecoverActivity.this, "Se ha enviado un mail a " + mail, Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getBaseContext(), LoginActivity.class);
                                            startActivity(i);
                                        } catch (MessagingException e) {
                                            e.printStackTrace();
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RecoverActivity.this);
                                        builder.setMessage("Envío fallido")
                                                .setNegativeButton("Volver", null)
                                                .create()
                                                .show();
                                    }
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };

                    RecoverRequest registerRequest = new RecoverRequest(username, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RecoverActivity.this);
                    queue.add(registerRequest);
            }
            }
        });
    }
}
