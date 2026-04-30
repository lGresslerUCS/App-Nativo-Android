package com.triple.app2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.triple.app2.R;
import com.triple.app2.model.Pessoa;
import com.triple.app2.repository.PessoaRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PessoaListActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdicionar;
    Button btnRefresh;
    PessoaRepository repository;

    private long ultimoTamanho = 0;
    private final String caminhoBanco =
            "/storage/emulated/0/Download/meubanco.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_list);

        listView = findViewById(R.id.lista);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnRefresh = findViewById(R.id.btnRefresh);

        repository = new PessoaRepository(this);

        repository.abrirBanco();

        btnAdicionar.setOnClickListener(v -> {
            startActivity(new Intent(this, PessoaFormActivity.class));
        });

        btnRefresh.setOnClickListener(v -> {
            repository.recarregarBanco();
            carregarPessoas();
        });

        iniciarMonitorBanco();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        repository.onBancoPronto(requestCode, resultCode, data);

        carregarPessoas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPessoas();
    }

    private void carregarPessoas() {

        List<Pessoa> pessoas = repository.todos();

        List<String> nomes = new ArrayList<>();

        if (pessoas.isEmpty()) {
            nomes.add("Nenhuma pessoa cadastrada");
        } else {
            for (Pessoa p : pessoas) {
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

    private void iniciarMonitorBanco() {

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                File file = new File(caminhoBanco);

                if (file.exists()) {

                    long tamanhoAtual = file.length();

                    if (tamanhoAtual != ultimoTamanho) {
                        ultimoTamanho = tamanhoAtual;

                        runOnUiThread(() -> carregarPessoas());
                    }
                }

                handler.postDelayed(this, 2000); // verifica a cada 2s
            }
        };

        handler.post(runnable);
    }
}