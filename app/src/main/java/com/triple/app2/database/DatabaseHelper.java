package com.triple.app2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {

    private static final String DB_PATH =
            "/storage/emulated/0/Download/meubanco.db";

    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {

        database = SQLiteDatabase.openOrCreateDatabase(
                DB_PATH,
                null
        );

        criarTabela();
    }

    private void criarTabela(){

        database.execSQL(
                "CREATE TABLE IF NOT EXISTS pessoa (" +
                        "id TEXT PRIMARY KEY," +
                        "nome TEXT," +
                        "idade INTEGER)"
        );

    }

    public SQLiteDatabase getDatabase(){
        return database;
    }
}