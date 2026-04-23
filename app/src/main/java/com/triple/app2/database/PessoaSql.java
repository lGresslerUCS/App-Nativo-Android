package com.triple.app2.database;

public class PessoaSql {

    public static final String CRIAR_TABELA =
            "CREATE TABLE pessoas (" +
                    "id TEXT PRIMARY KEY," +
                    "nome TEXT NOT NULL," +
                    "idade INTEGER NOT NULL)";
}