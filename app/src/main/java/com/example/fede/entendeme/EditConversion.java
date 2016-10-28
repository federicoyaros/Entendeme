package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fede on 28/10/2016.
 */
public class EditConversion extends ActionBarActivity {
    EditText etTitle, etConvertedText;
    ImageButton btnShare, btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_conversion);
        Bundle extras = getIntent().getExtras();
        etTitle = (EditText) findViewById(R.id.etTitle);
        etConvertedText = (EditText)  findViewById(R.id.etConvertedText);

        String title = extras.getString("title");
        String value = extras.getString("conversion");
        int userId = extras.getInt("userId");
        final int conversionId = extras.getInt("id");
        //Toast.makeText(EditConversion.this,String.valueOf(conversionId), Toast.LENGTH_SHORT).show();
        etTitle.setText(title);
        etConvertedText.setText(value);

        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnSave = (ImageButton) findViewById(R.id.btnSave);

        btnShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, etConvertedText.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String prueba = etTitle.getText().toString()
                if (etTitle.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditConversion.this);
                    builder.setMessage("Debe completar el título")
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
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                Bundle extras = getIntent().getExtras();
                                int id = extras.getInt("userId");
                                i.putExtra("id", id);
                                Toast.makeText(EditConversion.this, "Conversión modificada", Toast.LENGTH_SHORT).show();
                                startActivity(i);
                                //boolean success = jsonResponse.getBoolean("success");
                                //if (success)
                                //{
                                    //int id = Integer.parseInt(jsonResponse.getString("id"));
                                    //Intent i = new Intent(EditConversion.this, MainActivity.class);
                                    //i.putExtra("id", id);
                                    //startActivity(i);
                                //}
                                /*else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditConversion.this);
                                    builder.setMessage("Registro fallido")
                                            .setNegativeButton("Volver", null)
                                            .create()
                                            .show();
                                    }*/

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    Bundle extras = getIntent().getExtras();
                    int conversionId = extras.getInt("id");
                    String titulo = etTitle.getText().toString();
                    String conversion = etConvertedText.getText().toString();
                    EditConversionRequest editConversionRequest = new EditConversionRequest(String.valueOf(conversionId), titulo, conversion, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(EditConversion.this);
                    queue.add(editConversionRequest);
                //Intent i = new Intent(getBaseContext(), MainActivity.class);
                //startActivity(i);*/
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

