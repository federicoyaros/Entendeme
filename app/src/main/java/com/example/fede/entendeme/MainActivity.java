package com.example.fede.entendeme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.fede.entendeme.Constants.*;

public class MainActivity extends ActionBarActivity {

    private ArrayList<HashMap<String, String>> list;
    String url="http://entendemedb.esy.es/entendemeConnect/conversions.php";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECTED_PICTURE=2;
    private File output=null;
    final List<Integer> listViewIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        Intent mIntent = getIntent();
        final int userId = mIntent.getIntExtra("id", 0);

        ListView lv = (ListView)findViewById(R.id.listViewConversions);
        TextView txtNoConversions = (TextView) findViewById(R.id.txtNoConversions);
        //final List<Integer> listViewIds = new ArrayList<Integer>();
        final Downloader d=new Downloader(this,url,lv, txtNoConversions, userId, listViewIds);
        d.execute();
        int longitud = listViewIds.size();
        //Toast.makeText(getApplicationContext(), String.valueOf(longitud), Toast.LENGTH_LONG).show();
        registerForContextMenu(lv);
        lv.setItemsCanFocus(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                //int selectedId = listViewIds.get(position);
                //Intent i = new Intent(getBaseContext(), ConvertedText.class);
               // i.putExtra("selectedId", selectedId);
                //startActivity(i);
                int selectedId = listViewIds.get(position);
                //Toast.makeText(MainActivity.this, Integer.toString(selectedId), Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int id = Integer.parseInt(jsonResponse.getString("id"));
                            String title = jsonResponse.getString("title");
                            String conversion = jsonResponse.getString("conversion");
                            if (success) {
                                    /*Entendeme app = ((Entendeme)getApplicationContext());
                                    app.setUsuario(etUsername.getText().toString());*/
                                Intent i = new Intent(MainActivity.this, EditConversion.class);
                                i.putExtra("userId", userId);
                                i.putExtra("id", id);
                                i.putExtra("title", title);
                                i.putExtra("conversion", conversion);
                                //pd.dismiss();
                                startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ConversionRequest conversionRequest = new ConversionRequest(Integer.toString(selectedId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(conversionRequest);
            }

        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if(v.getId() == R.id.listViewConversions)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Opciones");
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for(int i = 0; i < menuItems.length; i++)
            {
                menu.add(menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        int index = info.position;
        int conversionId = listViewIds.get(index);
        //Toast.makeText(MainActivity.this, String.valueOf(conversionId), Toast.LENGTH_SHORT).show();
        if(menuItemIndex == 0){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        String conversion = jsonResponse.getString("conversion");
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, conversion);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            int indexPosition = info.position;
            int conversionIdSelected = listViewIds.get(indexPosition);
            ConversionRequest conversionRequest = new ConversionRequest(Integer.toString(conversionIdSelected), responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(conversionRequest);
        }
        else if(menuItemIndex == 1)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea eliminar la conversión?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try
                                    {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        Intent mIntent = getIntent();
                                        int id = mIntent.getIntExtra("id", 0);
                                        i.putExtra("id", id);
                                        Toast.makeText(MainActivity.this, "Conversión eliminada", Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            int index = info.position;
                            int conversionId = listViewIds.get(index);
                            DeleteConversionRequest deleteConversionRequest = new DeleteConversionRequest(String.valueOf(conversionId), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(deleteConversionRequest);
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Confirmación");
            alert.show();
        }
        //String listItemName = "hola";
        //Toast.makeText(MainActivity.this, String.valueOf(menuItemIndex), Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onClickFAB(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué imagen desea convertir?")
                .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File dir=
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        output = new File(dir, "ENTENDEME_" + timeStamp + ".jpg");
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                })
                .setNegativeButton("Adjuntar imagen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                                "content://media/internal/images/media"));
                        startActivity(intent);*/
                        Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, SELECTED_PICTURE);
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Seleccione");
        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, CheckPhoto.class);
            intent.putExtra("BitmapImage", imageBitmap);
            startActivity(intent);
        }*/
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode==RESULT_OK)
                {
                    /*Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");*/
                    Intent intent = new Intent(this, CheckPhoto.class);
                    //intent.putExtra("BitmapImage", imageBitmap);
                    Uri imageUri = Uri.fromFile(output);
                    intent.putExtra("ImageUri", imageUri);
                    startActivity(intent);
                }
                break;
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    /*Uri uri=data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex=cursor.getColumnIndex(projection[0]);
                    String filePath=cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d= new BitmapDrawable(yourSelectedImage);*/

                    /*Intent i = new Intent(this, CheckPhoto.class);
                    i.putExtra("BitmapImage", yourSelectedImage);
                    startActivity(i);*/

                    Uri imageUri = data.getData();
                    Intent i = new Intent(this, CheckAdjunt.class);
                    i.putExtra("ImageUri", imageUri);
                    startActivity(i);
                }
                break;

            default:
                break;
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
