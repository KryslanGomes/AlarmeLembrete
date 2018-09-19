package com.gomes.kryslan.alarmelembrete.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomes.kryslan.alarmelembrete.Controller.Tools;
import com.gomes.kryslan.alarmelembrete.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TelaDespertador extends Tools {
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
        //ImageView
    }
}
