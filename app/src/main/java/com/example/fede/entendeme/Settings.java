package com.example.fede.entendeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by fede on 8/9/2016.
 */
public class Settings extends ActionBarActivity {

    ListView listViewSettings;
    String[] settings = {"Cambiar mail", "Cambiar contraseña", "Acerca de Entendeme"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        listViewSettings = (ListView) findViewById(R.id.listViewSettings);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, settings);
        listViewSettings.setAdapter(adapter);
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
