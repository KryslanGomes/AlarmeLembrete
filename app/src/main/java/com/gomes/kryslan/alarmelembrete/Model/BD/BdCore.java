package com.gomes.kryslan.alarmelembrete.Model.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BdCore extends SQLiteOpenHelper {
    //GLOBAIS
    private static final String NOME_BD = "lembreteHoje";
    private static final int VERSAO_BD = 1;

    private Context c;

    //CHAMA/CRIA O BANCO.
    public BdCore(Context c){
        super(c, NOME_BD, null, VERSAO_BD);
        this.c = c;
    }

    //CREATE
    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE `lembretesAtivados` (" +
                "`_id`INTEGER NOT NULL PRIMARY KEY," +
                "`ativado`INTERGER);");

        /*bd.execSQL("CREATE TABLE `lembretesDeHoje` (" +
                "`_id`INTEGER NOT NULL PRIMARY KEY," +
                "`hora`INTERGER NOT NULL," +
                "`minuto`INTERGER NOT NULL," +
                "`lembrete`TEXT NOT NULL," +
                "`ativado`INTERGER);"); IMPLEMENTAÇÃO FUTURA COM O APP MAIS EVOLUÍDO******************

        bd.execSQL("CREATE TABLE `dataUltimoAcesso` (" +
                "`hoje`TEXT NOT NULL);");*/
    }

    //UPGRADE
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS lembretesAtivados");
        onCreate(bd);
    }
}
