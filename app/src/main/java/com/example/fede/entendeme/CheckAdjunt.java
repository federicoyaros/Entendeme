package com.example.fede.entendeme;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import java.util.Map;

/**
 * Created by fede on 2/9/2016.
 */
public class CheckAdjunt extends ActionBarActivity {
    private ImageView imgPhoto;
    private ImageButton btnCut, btnOk, btnAdjuntOtherPhoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int SELECTED_PICTURE = 2;
    public RequestQueue fRequestQueue;
    private static final String CONV_REQUEST_URL = "http://52.43.54.198:8080/entendeme/listaRequest/";
    public String pathToFile;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_adjunt);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCut = (ImageButton) findViewById(R.id.btnCut);
        btnAdjuntOtherPhoto = (ImageButton) findViewById(R.id.btnAdjuntOtherPhoto);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        Uri imageUri = intent.getParcelableExtra("ImageUri");
        imgPhoto.setImageURI(imageUri);
        fRequestQueue = Volley.newRequestQueue(CheckAdjunt.this);
        pathToFile = getRealPathFromURI(CheckAdjunt.this,imageUri);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);


        btnAdjuntOtherPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CheckAdjunt.this);
                builder.setMessage("En construcción")
                        .setNegativeButton("Volver", null)
                        .create()
                        .show();
            }
        });
    }

    public void onClickContinueAdjunt(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro que desea convertir esta imagen?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onPreStartConnection();
                        Entendeme app = ((Entendeme)getApplicationContext());

                        Map<String, String> ConvRequest= new HashMap<String, String>();
                        ConvRequest.put("nombreUsuario", app.getUsuario());
                        ConvRequest.put("nombreImagen", "test");
                        ConvRequest.put("formatoImagen", pathToFile.substring(pathToFile.length() - 4,pathToFile.length()));

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                CONV_REQUEST_URL, new JSONObject(ConvRequest),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        String url = null;
                                        try {
                                            url = response.getString( "rutaImagen" );
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }



                                        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {



                                            @Override
                                            public void onResponse(NetworkResponse response) {
                                                onConnectionFinished();
                                                String json = null;


                                                    json = new String(response.data);
                                                    json = trimMessage(json, "textoConvertido");

                                                Intent i = new Intent(getBaseContext(), ConvertedText.class);
                                                i.putExtra("ConvText", json);
                                                startActivity(i);

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                onConnectionFailed(error.toString());
                                            }
                                        }) {

                                            @Override
                                            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                                                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                                                InputStream is = null;
                                                try {
                                                    is = new BufferedInputStream(new FileInputStream(pathToFile));
                                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                    while (is.available() > 0) {
                                                        bos.write(is.read());
                                                    }
                                                    params.put("file", new DataPart("file_cover.jpg",bos.toByteArray() , "image/jpeg"));
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }



                                                return params;
                                            }
                                        };
                                        fRequestQueue.add(multipartRequest);


                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                onConnectionFailed(error.toString());
                            }
                        });

                        fRequestQueue.add(jsonObjReq);


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Confirmación");
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
                    Intent i = new Intent(this, CheckAdjunt.class);
                    i.putExtra("ImageUri", imageUri);
                    startActivity(i);
        }
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

    public void onPreStartConnection() {
        spinner.setVisibility(View.VISIBLE);
    }

    public void onConnectionFinished() {
        spinner.setVisibility(View.GONE);
    }

    public void onConnectionFailed(String error) {
        spinner.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
