package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fede on 30/10/2016.
 */
public class ChangePassword extends ActionBarActivity{

    EditText etNewPassword;
    Button btnChangePassword;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Intent mIntent = getIntent();
        final int userId = mIntent.getIntExtra("id", 0);

        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = etNewPassword.getText().toString();

                if (password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                    builder.setMessage("Debe completar la contraseña")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                }else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                    if (success)
                                    {
                                        Intent intent = new Intent(ChangePassword.this, Settings.class);
                                        Intent mIntent = getIntent();
                                        int userId = mIntent.getIntExtra("id", 0);
                                        intent.putExtra("id", userId);
                                        pd.dismiss();
                                        Toast.makeText(ChangePassword.this, "Contraseña modificada correctamente", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                    }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    pd=new ProgressDialog(ChangePassword.this);
                    pd.setTitle("Procesando");
                    pd.setMessage("Completando la acción...Por favor espere");
                    pd.show();
                    Intent mIntent = getIntent();
                    int userId = mIntent.getIntExtra("id", 0);
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(String.valueOf(userId), password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
                    queue.add(changePasswordRequest);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_settings:
            Intent intent = new Intent(getBaseContext(), Settings.class);
            Intent mIntent = getIntent();
            int id = mIntent.getIntExtra("id", 0);
            intent.putExtra("id", id);
            startActivity(intent);
            return(true);
        case R.id.action_logout:
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
