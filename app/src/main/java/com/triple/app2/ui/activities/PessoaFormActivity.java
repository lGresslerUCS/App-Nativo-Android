package com.triple.app2.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.triple.app2.R;
import com.triple.app2.model.Pessoa;
import com.triple.app2.repository.PessoaRepository;

import java.util.UUID;

public class PessoaFormActivity extends AppCompatActivity {

    EditText nome;
    EditText idade;
    Button salvar;

    PessoaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_form);

        nome = findViewById(R.id.nome);
        idade = findViewById(R.id.idade);
        salvar = findViewById(R.id.salvar);

        repository = new PessoaRepository(this);

        repository.abrirBanco();

        salvar.setOnClickListener(v -> {

            Pessoa pessoa = new Pessoa(
                    UUID.randomUUID().toString(),
                    nome.getText().toString(),
                    Integer.parseInt(idade.getText().toString())
            );

            repository.adicionar(pessoa);

            finish();
        });
    }
}