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

    private DatabaseHelper dbHelper;

    public PessoaRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void adicionar(Pessoa pessoa) {

        SQLiteDatabase db = dbHelper.getDatabase();

        ContentValues values = new ContentValues();
        values.put("id", pessoa.getId());
        values.put("nome", pessoa.getNome());
        values.put("idade", pessoa.getIdade());

        db.insert("pessoas", null, values);
    }

    public List<Pessoa> todos() {

        List<Pessoa> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM pessoa", null);

        if(cursor.moveToFirst()){
            do{
                Pessoa p = new Pessoa(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2)
                );

                lista.add(p);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }

    public void remover(String id){

        SQLiteDatabase db = dbHelper.getDatabase();

        db.delete("pessoa","id=?", new String[]{id});
    }
}