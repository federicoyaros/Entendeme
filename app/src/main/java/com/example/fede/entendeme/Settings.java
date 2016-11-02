package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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


        listViewSettings.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    Intent intent = new Intent(getBaseContext(), ChangeMail.class);
                    Intent mIntent = getIntent();
                    int userId = mIntent.getIntExtra("id", 0);
                    intent.putExtra("id", userId);
                    startActivity(intent);
                }else if(position == 1)
                {
                    Intent intent = new Intent(getBaseContext(), ChangePassword.class);
                    Intent mIntent = getIntent();
                    int userId = mIntent.getIntExtra("id", 0);
                    intent.putExtra("id", userId);
                    startActivity(intent);
                }else if(position == 2)
                {
                    Intent intent = new Intent(getBaseContext(), AboutEntendeme.class);
                    Intent mIntent = getIntent();
                    int userId = mIntent.getIntExtra("id", 0);
                    intent.putExtra("id", userId);
                    startActivity(intent);
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
            int userId = mIntent.getIntExtra("id", 0);
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
