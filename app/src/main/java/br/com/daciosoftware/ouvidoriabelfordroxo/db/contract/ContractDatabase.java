package br.com.daciosoftware.ouvidoriabelfordroxo.db.contract;

import android.provider.BaseColumns;

public class ContractDatabase {

    public static final String NOME_BANCO = "ouvidoriabelfordroxo.db";
    public static final int VERSAO = 1;

    public static abstract class Protocolo implements BaseColumns {
        //nome da tabela
        public static final String NOME_TABELA = "protocolo";
        // Colunas da tabela
        public static final String COLUNA_NUMERO = "numero";
        public static final String COLUNA_ANO = "ano";

        //Array das Colunas
        public static final String[] COLUNAS = {_ID, COLUNA_NUMERO, COLUNA_ANO};

        // Query de criação da tabela
        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + Protocolo.NOME_TABELA + "(" +
                        Protocolo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Protocolo.COLUNA_NUMERO + " INTEGER NOT NULL, " +
                        Protocolo.COLUNA_ANO + " INTEGER NOT NULL); " ;

        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    }



}
