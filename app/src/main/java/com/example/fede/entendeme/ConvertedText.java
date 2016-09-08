package com.example.fede.entendeme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by fede on 8/9/2016.
 */
public class ConvertedText extends ActionBarActivity {

    EditText etConvertedText;
    ImageButton btnShare, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converted_text);

        etConvertedText = (EditText)  findViewById(R.id.etConvertedText);
        etConvertedText.setText("Acá va el texto que está escrito en la imagen que elegiste :)");
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
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
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
