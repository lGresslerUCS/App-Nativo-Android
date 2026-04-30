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

    public void recarregarBanco() {
        dbHelper.recarregarBanco();
    }

    private SQLiteDatabase db() {
        return dbHelper.getDatabase();
    }

    public void adicionar(Pessoa pessoa) {

        SQLiteDatabase db = db();
        if (db == null) return;

        ContentValues values = new ContentValues();
        values.put("id", pessoa.getId());
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());

        db.insert("PESSOAS", null, values);
    }

    public List<Pessoa> todos() {

        List<Pessoa> lista = new ArrayList<>();

        SQLiteDatabase db = db();
        if (db == null) return lista;

        Cursor cursor = db.rawQuery("SELECT * FROM PESSOAS", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new Pessoa(
                        cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("idade"))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }

    public void remover(String id) {

        SQLiteDatabase db = db();
        if (db == null) return;

        db.delete("PESSOAS", "id=?", new String[]{id});
    }
}