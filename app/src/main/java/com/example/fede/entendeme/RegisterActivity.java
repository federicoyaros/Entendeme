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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etMail = (EditText) findViewById(R.id.etMail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = etUsername.getText().toString();
                final String mail = etMail.getText().toString();
                final String password = etPassword.getText().toString();

                if(username.equals("") || mail.equals("") || password.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Debe completar todos los datos")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                }else if(!emailValidator(mail))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("El formato de mail ingresado es inválido")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                }
                else
                {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            boolean existingUser = jsonResponse.getBoolean("existingUser");
                            if(existingUser)
                            {
                                pd.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Ya existe un usuario con esos datos")
                                        .setNegativeButton("Volver", null)
                                        .create()
                                        .show();
                            }
                            else{
                                if (success)
                                {
                                    int id = Integer.parseInt(jsonResponse.getString("id"));
                                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                    i.putExtra("id", id);
                                    //pd.dismiss();
                                    startActivity(i);
                                }
                                else
                                {
                                    pd.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Registro fallido")
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

                    pd=new ProgressDialog(RegisterActivity.this);
                    pd.setTitle("Procesando");
                    pd.setMessage("Completando la acción...Por favor espere");
                    pd.show();
                RegisterRequest registerRequest = new RegisterRequest(username, mail, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
            }
        });
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
