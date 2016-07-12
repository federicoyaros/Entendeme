package com.example.fede.entendeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by fede on 12/6/2016.
 */
public class Login extends Activity {

    private ImageButton btnSalir;
    private EditText etUsuario, etContraseña;
    private Button btnEntrar, btnOlvidoContraseña, btnRegistrarse;
    private CheckBox chkRecordarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnSalir = (ImageButton) findViewById(R.id.btnSalir);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnOlvidoContraseña = (Button) findViewById(R.id.btnOlvidoContraseña);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        chkRecordarme = (CheckBox) findViewById(R.id.chkRecordarme);
    }

    public void onClickSalir(View view){
        finish();
    }

    public void onClickEntrar (View view){
        Intent i = new Intent(getBaseContext(), PantallaPrincipal.class);
        startActivity(i);
    }
}
