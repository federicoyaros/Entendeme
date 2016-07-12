package com.example.fede.entendeme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by fede on 2/7/2016.
 */
public class PantallaPrincipal extends Activity {

    private Button btnConvertirTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        btnConvertirTexto = (Button) findViewById(R.id.btnConvertirTexto);

        btnConvertirTexto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add("Painting");
        menu.add("Photos");
        menu.add("Science");

        // Return true so that the menu gets displayed.
        return true;
    }




}
