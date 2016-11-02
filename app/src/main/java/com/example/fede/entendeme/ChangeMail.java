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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fede on 28/10/2016.
 */
public class ChangeMail extends ActionBarActivity {

    EditText etNewMail;
    Button btnChangeMail;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_mail);

        Intent mIntent = getIntent();
        final int userId = mIntent.getIntExtra("id", 0);

        etNewMail = (EditText) findViewById(R.id.etNewMail);
        btnChangeMail = (Button) findViewById(R.id.btnChangeMail);

        btnChangeMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = etNewMail.getText().toString();

                if (mail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMail.this);
                    builder.setMessage("Debe completar el mail")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                } else if(!emailValidator(mail))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMail.this);
                    builder.setMessage("El formato de mail ingresado es inválido")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                boolean existingMail = jsonResponse.getBoolean("existingMail");
                                if(existingMail)
                                {
                                    pd.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMail.this);
                                    builder.setMessage("El mail ingresado ya está registrado, por favor indique otro")
                                            .setNegativeButton("Volver", null)
                                            .create()
                                            .show();
                                }
                                else{
                                    if (success)
                                    {
                                        Intent intent = new Intent(ChangeMail.this, Settings.class);
                                        Intent mIntent = getIntent();
                                        int userId = mIntent.getIntExtra("id", 0);
                                        intent.putExtra("id", userId);
                                        pd.dismiss();
                                        Toast.makeText(ChangeMail.this, "Mail modificado correctamente", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMail.this);
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
                    pd=new ProgressDialog(ChangeMail.this);
                    pd.setTitle("Procesando");
                    pd.setMessage("Completando la acción...Por favor espere");
                    pd.show();
                    Intent mIntent = getIntent();
                    int userId = mIntent.getIntExtra("id", 0);
                    ChangeMailRequest changeMailRequest = new ChangeMailRequest(String.valueOf(userId), mail, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangeMail.this);
                    queue.add(changeMailRequest);
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
