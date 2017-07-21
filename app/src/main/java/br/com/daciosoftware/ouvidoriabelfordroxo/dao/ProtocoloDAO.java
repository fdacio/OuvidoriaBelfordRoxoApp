package br.com.daciosoftware.ouvidoriabelfordroxo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.daciosoftware.ouvidoriabelfordroxo.Protocolo;
import br.com.daciosoftware.ouvidoriabelfordroxo.db.Database;
import br.com.daciosoftware.ouvidoriabelfordroxo.db.contract.ContractDatabase;
import br.com.daciosoftware.ouvidoriabelfordroxo.util.MyDateUtil;

/**
 * Created by fdacio on 06/07/17.
 */
public class ProtocoloDAO {

    private SQLiteDatabase db;

    public ProtocoloDAO(Context context) {
        this.db = Database.getDatabase(context);
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public  Long save(Protocolo protocolo) throws SQLiteException{
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.Protocolo.COLUNA_NUMERO, protocolo.getNumero());
        values.put(ContractDatabase.Protocolo.COLUNA_ANO, protocolo.getAno());
        return this.getDb().insertOrThrow(ContractDatabase.Protocolo.NOME_TABELA, "", values);
    }

    public Protocolo getEntity(Cursor c){
        if (c.getCount() > 0) {
            Protocolo protocolo = new Protocolo();
            protocolo.setId(c.getInt(0));
            protocolo.setNumero(c.getInt(1));
            protocolo.setAno(c.getInt(2));
            return protocolo;
        } else {
            return null;
        }
    }

    public List<Protocolo> listAll() {
        List<Protocolo> listProtocolo = new ArrayList<>();
        try {
            Cursor cursor = getCursor(null, null);
            if (cursor.moveToFirst()) {
                do {
                    Protocolo protocolo = getEntity(cursor);
                    listProtocolo.add(protocolo);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new RuntimeException();

        }
        return listProtocolo;
    }

    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.Protocolo.NOME_TABELA,
                ContractDatabase.Protocolo.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }
}
