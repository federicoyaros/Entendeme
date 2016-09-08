package com.example.fede.entendeme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.fede.entendeme.Constants.*;

public class MainActivity extends ActionBarActivity {

    private ArrayList<HashMap<String, String>> list;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECTED_PICTURE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        ListView listView=(ListView)findViewById(R.id.listViewConversions);

        list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> temp=new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Apuntes de la facu");
        temp.put(SECOND_COLUMN, "15/08/16");
        list.add(temp);

        HashMap<String,String> temp2=new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "Receta de cocina");
        temp2.put(SECOND_COLUMN, "19/08/16");
        list.add(temp2);

        HashMap<String,String> temp3=new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "Letra de canción");
        temp3.put(SECOND_COLUMN, "20/08/16");
        list.add(temp3);

        HashMap<String,String> temp4=new HashMap<String, String>();
        temp4.put(FIRST_COLUMN, "Notas varias");
        temp4.put(SECOND_COLUMN, "15/08/16");
        list.add(temp4);

        HashMap<String,String> temp5=new HashMap<String, String>();
        temp5.put(FIRST_COLUMN, "Mensaje para Nico");
        temp5.put(SECOND_COLUMN, "19/08/16");
        list.add(temp5);

        HashMap<String,String> temp6=new HashMap<String, String>();
        temp6.put(FIRST_COLUMN, "Apuntes de inglés");
        temp6.put(SECOND_COLUMN, "20/08/16");
        list.add(temp6);

        HashMap<String,String> temp7=new HashMap<String, String>();
        temp7.put(FIRST_COLUMN, "Primera conversión");
        temp7.put(SECOND_COLUMN, "15/08/16");
        list.add(temp7);

        HashMap<String,String> temp8=new HashMap<String, String>();
        temp8.put(FIRST_COLUMN, "Hola cómo va?");
        temp8.put(SECOND_COLUMN, "19/08/16");
        list.add(temp8);

        HashMap<String,String> temp9=new HashMap<String, String>();
        temp9.put(FIRST_COLUMN, "Todo bien");
        temp9.put(SECOND_COLUMN, "20/08/16");
        list.add(temp9);

        ListViewConversionsAdapter adapter = new ListViewConversionsAdapter(this, list);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                Intent i = new Intent(getBaseContext(), ConvertedText.class);
                startActivity(i);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = "hola";
        return true;
    }

    public void onClickFAB(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué imagen desea convertir?")
                .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Intent intent = new Intent(this, CheckPhoto.class);
                    intent.putExtra("BitmapImage", imageBitmap);
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
