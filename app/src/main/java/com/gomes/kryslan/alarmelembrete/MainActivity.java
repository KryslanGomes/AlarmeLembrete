package com.gomes.kryslan.alarmelembrete;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gomes.kryslan.alarmelembrete.Controller.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Tools {
    Activity a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NomeiaCalendario();
            }
        });

        //CALENDARIO
        NomeiaCalendario();
    }

    private void NomeiaCalendario() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            SnackBar(a, "Conceda permição para ler o calendário para ativar a integração.");
            //System.exit(1);  //Fecha o app IMPLEMENTAR *************
            //return;
        }

        //PEGA INFORMAÇÕES DO CALENDÁRIO
        String[] colunas = new String[] {
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,  //Data que o evento começa.
                CalendarContract.Events.DTEND,  //Data que o evento termina.
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.EVENT_LOCATION};
        //O mês começa no 0 então: 0 = Janeiro, 1 = Fevereiro, etc...

        //Define o dia que começa a pegar as informações:
        Date hoje = Calendar.getInstance().getTime();
        Locale local = getResources().getConfiguration().locale;  //Pega o local para definir o tempo corretamente baseado no fuso horário.
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy", local);
        int ano = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("MM", local);
        int mes = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("dd", local);
        int dia = Integer.parseInt(dataFormat.format(hoje));

        //DEFINE O RANGE DE DATA COMO APENAS HOJE:
        Calendar dataComeco = Calendar.getInstance();
        dataComeco.set(ano,mes-1,dia,0,0);  //(Mês é -1 pois o mês na tabela começa no 0).

        Calendar dataFim= Calendar.getInstance();
        dataFim.set(ano,mes-1,dia,23,59);  //(Mês é -1 pois o mês na tabela começa no 0).

        String where = "(( " + CalendarContract.Events.DTSTART + " >= " + dataComeco.getTimeInMillis() +
                       " ) AND ( " +  //where data do evento seja hoje.
                       CalendarContract.Events.DTSTART + " <= " + dataFim.getTimeInMillis() + " ))";

        Cursor cursor = this.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, colunas, where, null, null );

        dataFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm", local);
        String diaCompleto = dataFormat.format(hoje);
        if (cursor.moveToFirst()){
            do {
                Toast("Título: " + cursor.getString(1) + " Começa: " + diaCompleto);
            } while ( cursor.moveToNext());
        }
    }

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
