package com.gomes.kryslan.alarmelembrete.Controller;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Tools extends AppCompatActivity {
    public void SnackBar(Activity a, String mensagem){
        Snackbar.make(a.findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void Toast(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
    public void Toast(String mensagem, int duracao){
        Toast.makeText(this, mensagem, duracao).show();
    }
}
