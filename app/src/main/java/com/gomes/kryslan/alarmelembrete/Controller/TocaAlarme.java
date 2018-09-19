package com.gomes.kryslan.alarmelembrete.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TocaAlarme extends Service {

    @Override
    public void onCreate() {
        Toast.makeText(this, "O CELULAR ESTÁ DESPERTANDOOOOOOOOO", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

}