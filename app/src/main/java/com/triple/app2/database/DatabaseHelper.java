package com.triple.app2.database;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DatabaseHelper {

    private static final int REQUEST_DB = 100;

    private final Activity activity;
    private SQLiteDatabase database;
    private Uri bancoUri;

    public DatabaseHelper(Activity activity) {
        this.activity = activity;
    }

    // abre seletor de arquivo
    public void selecionarBanco() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.startActivityForResult(intent, REQUEST_DB);
    }

    // recebe resultado do seletor
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_DB && resultCode == Activity.RESULT_OK && data != null) {

            bancoUri = data.getData();
            abrirBanco(bancoUri);
        }
    }

    // 🔥 CORREÇÃO PRINCIPAL
    public void abrirBanco(Uri uri) {

        try {
            if (uri == null) return;

            InputStream input = activity.getContentResolver().openInputStream(uri);

            File dbFile = activity.getDatabasePath("meubanco.db");

            FileOutputStream output = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int len;

            while ((len = input.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }

            output.close();
            input.close();

            database = SQLiteDatabase.openDatabase(
                    dbFile.getPath(),
                    null,
                    SQLiteDatabase.OPEN_READWRITE
            );

            Log.d("DATABASE", "BANCO ABERTO EM: " + database.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recarregarBanco() {
        if (bancoUri != null) {
            abrirBanco(bancoUri);
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public boolean isBancoAberto() {
        return database != null;
    }
}