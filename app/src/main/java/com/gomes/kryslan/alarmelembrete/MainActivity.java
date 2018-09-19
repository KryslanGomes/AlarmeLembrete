package com.gomes.kryslan.alarmelembrete;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gomes.kryslan.alarmelembrete.Controller.TocaAlarme;
import com.gomes.kryslan.alarmelembrete.Controller.Tools;
import com.gomes.kryslan.alarmelembrete.View.FragmentAlarmes;
import com.gomes.kryslan.alarmelembrete.View.TelaDespertador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Tools {
    Activity a = this;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(c.getResources().getString(R.string.lembretes));

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(c, TelaDespertador.class);
                startActivity(intent);


                /*Intent myIntent = new Intent(MainActivity.this, TocaAlarme.class);
                pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                AlarmManager.AlarmClockInfo abelha = alarmManager.getNextAlarmClock();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 10);

                Date hoje = Calendar.getInstance().getTime();
                Locale local = getResources().getConfiguration().locale;
                SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", local);
                String teste = dataFormat.format(hoje);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 60000, pendingIntent);

                abelha = alarmManager.getNextAlarmClock();

                Toast("Start Alarm: " + calendar.getTimeInMillis());*/

                /*String nomePackageCalendario = CalendarContract.Events.CUSTOM_APP_PACKAGE;
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(nomePackageCalendario);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }else{
                    SnackBar(a,"Aplicativo agenda não encontrado em seu celular, instále-o.");
                }*/
            }
        });

        //CARREGAR FRAGMENT
        Fragment fragment;
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment =  new FragmentAlarmes();

        ft.replace(R.id.fragment, fragment).commit();
    }

    //ATUALIZA OS ALARMES onResume
    @Override
    public void onResume(){
        super.onResume();

        Fragment fragment;
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment =  new FragmentAlarmes();
        ft.replace(R.id.fragment, fragment).commit();
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
