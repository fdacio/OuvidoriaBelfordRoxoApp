package br.com.daciosoftware.ouvidoriabelfordroxo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.daciosoftware.ouvidoriabelfordroxo.db.contract.ContractDatabase;

public class SQLiteHelperDatabase extends SQLiteOpenHelper {

    public SQLiteHelperDatabase(Context context) {
        super(context, ContractDatabase.NOME_BANCO, null, ContractDatabase.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractDatabase.Protocolo.SQL_CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        if(novaVersao > versaoAntiga){
            db.execSQL(ContractDatabase.Protocolo.SQL_DELETA_TABELA);
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

}
