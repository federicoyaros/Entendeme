package com.example.fede.entendeme;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by fede on 31/8/2016.
 */
public class RecoverActivity extends AppCompatActivity {

    EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);

        etUsername = (EditText) findViewById(R.id.etUsername);
    }

    public void onClickRecover (View view)
    {
        final String username = etUsername.getText().toString();

        if (username.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RecoverActivity.this);
            builder.setMessage("Debe completar los datos")
                    .setNegativeButton("Volver", null)
                    .create()
                    .show();
        } else {
            SendMail sm = new SendMail(this, "fede.yaros@gmail.com", "probando", "hola como va");
            sm.execute();
        }


    }
}
