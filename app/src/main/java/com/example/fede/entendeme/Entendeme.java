package com.example.fede.entendeme;

import android.app.Application;

/**
 * Created by Administrator on 15/09/2016.
 */
public class Entendeme extends Application {

    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}