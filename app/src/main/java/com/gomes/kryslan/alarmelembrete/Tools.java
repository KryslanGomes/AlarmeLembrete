package com.gomes.kryslan.alarmelembrete;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

public class Tools extends AppCompatActivity {
    public void SnackBar(Activity a, String mensagem){
        Snackbar.make(a.findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
