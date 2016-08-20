package com.example.fede.entendeme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.ContextMenu;
//import android.view.Gravity;
//import android.view.Menu;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

/**
 * Created by fede on 2/7/2016.
 */
public class PantallaPrincipal extends Activity implements OnMenuItemClickListener{

    private Button btnConvertirTexto;
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        btnConvertirTexto = (Button) findViewById(R.id.btnConvertirTexto);
    }

    public void showPopUp (View v){
        PopupMenu popUp = new PopupMenu(this, v);
        popUp.setOnMenuItemClickListener(this);
        MenuInflater inflater = popUp.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popUp.getMenu());
        popUp.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.opcionTomarFoto:
                this.abrirCamara();
                return true;
            case R.id.opcionAdjuntarImagen:
                Toast.makeText(getBaseContext(), "elegiste 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public void abrirCamara(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    //@Override
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(getBaseContext(), TomarFoto.class);
            intent.putExtra("foto", imageBitmap);
            startActivity(intent);
        }
    }*/
}
