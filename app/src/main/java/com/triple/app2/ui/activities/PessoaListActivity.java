package com.triple.app2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.triple.app2.R;
import com.triple.app2.model.Pessoa;
import com.triple.app2.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

public class PessoaListActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdicionar;
    PessoaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_list);

        listView = findViewById(R.id.lista);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        repository = new PessoaRepository(this);

        btnAdicionar.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PessoaListActivity.this,
                    PessoaFormActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPessoas();
    }

    private void carregarPessoas(){

        List<Pessoa> pessoas = repository.todos();

        List<String> nomes = new ArrayList<>();

        if(pessoas.isEmpty()){
            nomes.add("Nenhuma pessoa cadastrada");
        } else {
            for(Pessoa p : pessoas){
                nomes.add(p.getNome() + " - " + p.getIdade());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nomes
        );

        listView.setAdapter(adapter);
    }
}