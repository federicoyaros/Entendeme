package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fede on 8/9/2016.
 */
public class ConvertedText extends ActionBarActivity {

    EditText etConvertedText;
    ImageButton btnShare, btnSave;
    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converted_text);
        Bundle extras = getIntent().getExtras();
        etConvertedText = (EditText)  findViewById(R.id.etConvertedText);

        if (extras != null) {
            String value = extras.getString("ConvText");
            etConvertedText.setText(value);
        }
        else{
            String value = "No se ha logrado la conversión";
            etConvertedText.setText(value);
        }

        Intent mIntent = getIntent();
        userId = mIntent.getIntExtra("id", 0);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ConvertedText.this);
                builder.setTitle("Por favor ingrese un título");
                final EditText input = new EditText(ConvertedText.this);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titulo = input.getText().toString();
                        if(titulo.equals("")){
                            Toast.makeText(ConvertedText.this, "Debe completar el título", Toast.LENGTH_LONG).show();
                        }else
                        {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            Intent i = new Intent(ConvertedText.this, MainActivity.class);
                                            i.putExtra("id", userId);
                                            startActivity(i);
                                        } else {
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            String conversion = etConvertedText.getText().toString().trim();
                            String conversionRemoveBlank = conversion.replace("\n", " ");
                            String conversionRemoveQuote = conversionRemoveBlank.replace("\'", "");
                            String conversionFinal = conversionRemoveQuote.replace("\"", "");
                            String title = titulo;
                            InsertConversionRequest insertConversionRequest = new InsertConversionRequest(String.valueOf(userId), title, conversionFinal, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ConvertedText.this);
                            queue.add(insertConversionRequest);
                        }
                    }
                });

                /*Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                //Intent i = new Intent(getBaseContext(), MainActivity.class);
                                //i.putExtra("id", id);

                                //startActivity(i);
                            } else {
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                String conversion = etConvertedText.getText().toString().trim();
                String conversionRemoveBlank = conversion.replace("\n", " ");
                String conversionRemoveQuote = conversionRemoveBlank.replace("\'", "");
                String conversionFinal = conversionRemoveQuote.replace("\"", "");
                String title = "Hola";
                InsertConversionRequest insertConversionRequest = new InsertConversionRequest(String.valueOf(userId), title, conversionFinal, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ConvertedText.this);
                queue.add(insertConversionRequest);*/
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
            intent.putExtra("id", userId);
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
