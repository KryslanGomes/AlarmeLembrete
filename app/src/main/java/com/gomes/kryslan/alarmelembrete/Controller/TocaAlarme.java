package com.gomes.kryslan.alarmelembrete.Controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.gomes.kryslan.alarmelembrete.View.TelaDespertador;

public class TocaAlarme extends Service {
    Context c = this;
    @Override
    public void onCreate() {
        final Intent intent = new Intent(c, TelaDespertador.class);
        startActivity(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
