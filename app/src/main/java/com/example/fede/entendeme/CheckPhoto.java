package com.example.fede.entendeme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fede on 2/9/2016.
 */
public class CheckPhoto extends ActionBarActivity {

    private ImageView imgPhoto;
    private ImageButton btnCut, btnTakeOtherPhoto, btnOk;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public RequestQueue fRequestQueue;
    private static final String CONV_REQUEST_URL = "http://52.43.54.198:8080/entendeme/listaRequest/";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_photo);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCut = (ImageButton) findViewById(R.id.btnCut);
        btnTakeOtherPhoto = (ImageButton) findViewById(R.id.btnTakeOtherPhoto);
        btnOk = (ImageButton) findViewById(R.id.btnOk);

        Intent intent = getIntent();
        Bitmap imageBitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        imgPhoto.setImageBitmap(imageBitmap);
        fRequestQueue = Volley.newRequestQueue(CheckPhoto.this);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CheckPhoto.this);
                builder.setMessage("En construcción")
                        .setNegativeButton("Volver", null)
                        .create()
                        .show();
            }
        });
    }

    public void onClickTakeOtherPhoto(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onClickContinuePhoto(View view)
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
                        ConvRequest.put("formatoImagen", ".jpg");

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
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<>();
                                                //params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                                                return params;
                                            }

                                            @Override
                                            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                                                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                                                // file name could found file base or direct access from real path
                                                // for now just get bitmap data from ImageView
                                                /*InputStream is = null;
                                                try {
                                                    is = new BufferedInputStream(new FileInputStream(pathToFile));
                                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                    while (is.available() > 0) {
                                                        bos.write(is.read());
                                                    }*/
                                                    params.put("file", new DataPart("file_cover.jpg",AppHelper.getFileDataFromDrawable(getBaseContext(), imgPhoto.getDrawable()) , "image/jpeg"));
                                                /*} catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }*/



                                                return params;
                                            }
                                        };
                                        fRequestQueue.add(multipartRequest);

                                        //onConnectionFinished();


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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, CheckPhoto.class);
            intent.putExtra("BitmapImage", imageBitmap);
            startActivity(intent);
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
}
