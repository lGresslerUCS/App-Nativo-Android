package com.triple.app2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triple.app2.database.DatabaseHelper;
import com.triple.app2.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaRepository {

    private final DatabaseHelper dbHelper;

    public PessoaRepository(Context context) {
        dbHelper = new DatabaseHelper((android.app.Activity) context);
    }

    public void abrirBanco() {
        dbHelper.selecionarBanco();
    }

    public void onBancoPronto(int requestCode, int resultCode, android.content.Intent data) {
        dbHelper.onActivityResult(requestCode, resultCode, data);
    }

    private SQLiteDatabase db() {
        return dbHelper.getDatabase();
    }

    // 🔥 GARANTE BANCO ABERTO
    private boolean garantirBancoAberto() {
        if (dbHelper.getDatabase() == null || !dbHelper.isBancoAberto()) {
            dbHelper.abrirBanco();
        }
        return dbHelper.getDatabase() != null;
    }

    public void adicionar(Pessoa pessoa) {

        if (!garantirBancoAberto()) return;

        SQLiteDatabase db = dbHelper.getDatabase();

        ContentValues values = new ContentValues();
        values.put("id", pessoa.getId());
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());

        db.insert("pessoas", null, values);
    }

    public List<Pessoa> todos() {

        List<Pessoa> lista = new ArrayList<>();

        if (!garantirBancoAberto()) return lista;

        SQLiteDatabase db = dbHelper.getDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM pessoas", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new Pessoa(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }

    public void remover(String id) {

        if (!garantirBancoAberto()) return;

        SQLiteDatabase db = dbHelper.getDatabase();

        db.delete("pessoas", "id=?", new String[]{id});
    }
}