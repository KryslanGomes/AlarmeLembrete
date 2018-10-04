package com.gomes.kryslan.alarmelembrete.View;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gomes.kryslan.alarmelembrete.Controller.Tools;
import com.gomes.kryslan.alarmelembrete.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TelaDespertador extends Tools{  //Para fazer certo.
    Context c = this;
    Activity a = this;
    MediaPlayer mediaPlayer;
    Vibrator vibrate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despertador);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        //SETANDO HORA
        TextView txHora = findViewById(R.id.hora);
        TextView txMinuto = findViewById(R.id.minuto);

        Date hoje = Calendar.getInstance().getTime();
        Locale local = getResources().getConfiguration().locale;  //Pega o local para definir o tempo corretamente baseado no fuso horário.
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH", local);
        SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm", local);

        txHora.setText(formatoHora.format(hoje.getTime()));
        txMinuto.setText(formatoMinuto.format(hoje.getTime()));

        //BOTÃO ALARME ARRASTAR
        //IniciaControleArrastar();

        //LISTENER BOTÕES
        ImageView btSoneca = findViewById(R.id.imgSoneca);
        ImageView btDesligaAlarme = findViewById(R.id.imgDesligaAlarme);

        btSoneca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Soneca precionado", Toast.LENGTH_SHORT).show();
                mediaPlayer.stop();
                mediaPlayer.release();
                vibrate.cancel();
                a.finish();
            }
        });

        btDesligaAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Desligar alarme precionado", Toast.LENGTH_SHORT).show();

                mediaPlayer.stop();
                mediaPlayer.release();
                vibrate.cancel();
                a.finish();
            }
        });

        //MÚSICA
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarme_sonoro);

        mediaPlayer.start();

        //VIBRAÇÃO
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        long[] pattern = {0, 100, 1000};

        if (vibrate != null) {
            vibrate.vibrate(pattern, 0);
        }
    }

    /*private void IniciaControleArrastar(){
        ImageView btPrincipal = findViewById(R.id.btPrincipalAlarme);
        ImageView btSoneca = findViewById(R.id.btSoneca);
        ImageView btDesligaAlarme = findViewById(R.id.btDesligaAlarme);
        //Seta uma tag para o botão.
        btPrincipal.setTag("Button");

        //Listener dragView
        btPrincipal.setOnTouchListener(new DragDropOnTouchListener());

        //Listener dropView
        btSoneca.setOnD
    }*/
}
