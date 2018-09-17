package com.gomes.kryslan.alarmelembrete.Model.BD;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.gomes.kryslan.alarmelembrete.Model.Alarmes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BdLite {
    private static SQLiteDatabase bd;

    public BdLite(Context c) {
        BdCore bdCore = new BdCore(c);
        bd = bdCore.getWritableDatabase();
    }

    //SELECT
    @SuppressLint("Recycle")
    public static List<Alarmes> SelectAlarmesHoje() {
        List<Alarmes> list = new ArrayList<>();
        String[] colunas;
        @SuppressLint("Recycle") Cursor cursor;

        colunas = new String[]{"_id", "hora", "minuto", "lembrete", "ativado"};
        cursor = bd.query("lembretesDeHoje", colunas, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Alarmes a = new Alarmes();
                a.setId(cursor.getInt(0));
                a.setHora(cursor.getInt(1));
                a.setMinuto(cursor.getInt(2));
                a.setLembrete(cursor.getString(3));
                a.setAtivado(cursor.getInt(4));
                list.add(a);
            } while (cursor.moveToNext());
        }
        return (list);
    }

    @SuppressLint("Recycle")
    public static String SelectUltimaData(Context c) {  //Seleciona a última data que o app foi aberto.
        String[] colunas;
        @SuppressLint("Recycle") Cursor cursor;

        colunas = new String[]{"hoje"};
        cursor = bd.query("dataUltimoAcesso", colunas, null, null, null, null, null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }else if(cursor.getCount() == 0){  //Caso o app nunca tenha sido aberto, logo não terá última data de abertura (POIS NUNCA FOI ABERTO).
            InsertDataDeHoje();
            return "0";
        }else{
            Toast.makeText(c, "ERRO no banco de dados: existe mais de uma data.", Toast.LENGTH_LONG).show();
            return "";  //FAZER O MESMO QUE O ELSE ANTERIOR. (REINICIAR O BANCO)
        }
    }

    //INSERT
    public void InsertLembretesHoje(int idPratica) {
        ContentValues valores = new ContentValues();
        valores.put("id_pratica", idPratica);

        bd.insert("historico", null, valores);
    }

    private static void InsertDataDeHoje() {
        ContentValues valores = new ContentValues();

        Date hoje = Calendar.getInstance().getTime();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");

        valores.put("hoje", dataFormat.format(hoje));

        bd.insert("dataUltimoAcesso", null, valores);
    }

    //DELETA (DROP)
    /*public void DeletarHistoricoPesquisa(TelaInicialCards historicoSearch) {
        bd.delete("historicoPesquisa", "_id = ? ", new String[]{"" + historicoSearch.getId()});  //A "?" do segundo parâmetro será substituído pelo terceiro parâmetro.
    }

    public void DeletarTudoHistoricoPesquisa() {
        bd.execSQL("delete from " + "historicoPesquisa");
    }*/

    //UPDATE
    /*public static void AtualizaDataDeHoje(){
        ContentValues valores = new ContentValues();

        valores.put("tipoLista", tipoLista);

        bd.update("tipoLista", valores, null,  null);
    }*/

    //PEGA LEMBRETES DE HOJE NO CALENDÁRIO
    private List<Alarmes> AcessaCalendario(Context c) {
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            SnackBar(a, "Conceda permição para ler o calendário para ativar a integração.");
            //System.exit(1);  //Fecha o app IMPLEMENTAR *************
            //return;
        }*/

        //PEGA INFORMAÇÕES DO CALENDÁRIO
        String[] colunas = new String[]{
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
        Locale local = c.getResources().getConfiguration().locale;  //Pega o local para definir o tempo corretamente baseado no fuso horário.
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy", local);
        int ano = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("MM", local);
        int mes = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("dd", local);
        int dia = Integer.parseInt(dataFormat.format(hoje));

        //DEFINE O RANGE DE DATA COMO APENAS HOJE:
        Calendar dataComeco = Calendar.getInstance();
        dataComeco.set(ano, mes - 1, dia, 0, 0);  //(Mês é -1 pois o mês na tabela começa no 0).

        Calendar dataFim = Calendar.getInstance();
        dataFim.set(ano, mes - 1, dia, 23, 59);  //(Mês é -1 pois o mês na tabela começa no 0).

        String where = "(( " + CalendarContract.Events.DTSTART + " >= " + dataComeco.getTimeInMillis() +
                " ) AND ( " +  //where data do evento seja hoje.
                CalendarContract.Events.DTSTART + " <= " + dataFim.getTimeInMillis() + " ))";

        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Cursor cursor = c.getContentResolver().query(CalendarContract.Events.CONTENT_URI, colunas, where, null, null);

        dataFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm", local);
        //String diaCompleto = dataFormat.format(hoje);??????????
        List<Alarmes> list = new ArrayList<>();
        int id = 0;
        if (cursor.moveToFirst()){
            do {
                Alarmes a = new Alarmes();
                a.setId(id);
                a.setHora(cursor.getInt(0));
                a.setMinuto(cursor.getInt(1));
                a.setLembrete(cursor.getString(2));
                a.setAtivado(cursor.getInt(3));
                list.add(a);
                id++;
            } while(cursor.moveToNext());
        }

        //MATA O CURSOR
        cursor.close();

        return list;
    }
}
