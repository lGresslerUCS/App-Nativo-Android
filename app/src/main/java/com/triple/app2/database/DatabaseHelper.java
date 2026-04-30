package com.triple.app2.database;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DatabaseHelper {

    private static final int REQUEST_DB = 100;

    private final Activity activity;
    private SQLiteDatabase database;

    public DatabaseHelper(Activity activity) {
        this.activity = activity;
    }

    // abre seletor do arquivo (Downloads)
    public void selecionarBanco() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.startActivityForResult(intent, REQUEST_DB);
    }

    // recebe resultado do seletor
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_DB &&
                resultCode == Activity.RESULT_OK &&
                data != null) {

            try {
                Uri uri = data.getData();

                copiarBanco(uri);
                abrirBanco();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // copia arquivo selecionado para interno
    private void copiarBanco(Uri uri) throws Exception {

        InputStream input = activity.getContentResolver().openInputStream(uri);

        File dbFile = activity.getDatabasePath("meubanco.db");

        FileOutputStream output = new FileOutputStream(dbFile);

        byte[] buffer = new byte[8192];
        int len;

        while ((len = input.read(buffer)) > 0) {
            output.write(buffer, 0, len);
        }

        output.flush();
        output.close();
        input.close();
    }

    // abre SQLite interno (SEGURO)
    public void abrirBanco() {

        File dbFile = activity.getDatabasePath("meubanco.db");

        database = SQLiteDatabase.openDatabase(
                dbFile.getPath(),
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public boolean isBancoAberto() {
        return database != null;
    }
}