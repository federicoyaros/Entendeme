package com.example.fede.entendeme;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import static com.example.fede.entendeme.Constants.*;

public class MainActivity extends ActionBarActivity {

    private ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        ListView listView=(ListView)findViewById(R.id.listViewConversions);

        list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> temp=new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Primera conversión");
        temp.put(SECOND_COLUMN, "15/08/16");
        list.add(temp);

        HashMap<String,String> temp2=new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "Hola cómo va?");
        temp2.put(SECOND_COLUMN, "19/08/16");
        list.add(temp2);

        HashMap<String,String> temp3=new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "Todo bien");
        temp3.put(SECOND_COLUMN, "20/08/16");
        list.add(temp3);

        HashMap<String,String> temp4=new HashMap<String, String>();
        temp4.put(FIRST_COLUMN, "Primera conversión");
        temp4.put(SECOND_COLUMN, "15/08/16");
        list.add(temp4);

        HashMap<String,String> temp5=new HashMap<String, String>();
        temp5.put(FIRST_COLUMN, "Hola cómo va?");
        temp5.put(SECOND_COLUMN, "19/08/16");
        list.add(temp5);

        HashMap<String,String> temp6=new HashMap<String, String>();
        temp6.put(FIRST_COLUMN, "Todo bien");
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(MainActivity.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void onClickFAB(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué imagen desea convertir?")
                .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Adjuntar imagen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Seleccione");
        alert.show();

    }
}
