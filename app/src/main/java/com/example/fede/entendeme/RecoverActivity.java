package com.example.fede.entendeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by fede on 31/8/2016.
 */
public class RecoverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);
    }

    public void onClickRecover (View view)
    {
        SendMail sm = new SendMail(this, "fede.yaros@gmail.com", "probando", "hola como va");
        sm.execute();


    }
}
