package com.example.desafiomobits.Opcoes;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.desafiomobits.Database.ScriptBD;
import com.example.desafiomobits.Geral;
import com.example.desafiomobits.Movimentacao.Movimentacao;
import com.example.desafiomobits.Movimentacao.MovimentacaoListAdapter;
import com.example.desafiomobits.Movimentacao.MovimentacaoRepositorio;
import com.example.desafiomobits.R;

import java.util.ArrayList;

public class ExtratoActivity extends AppCompatActivity {

    private ListView lstViewItem;

    private ArrayList<Movimentacao> extrato;
    private MovimentacaoRepositorio movimentacaoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extrato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstViewItem = findViewById(R.id.lstViewItem);


        int id_usuario =  getIntent().getIntExtra("id_usuario",-1);

        if(id_usuario == -1){
            Geral.AlertaNeutro(this,"Erro","Erro ao pegar id do usuario","Sair",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
        }

        SQLiteDatabase conexao = ScriptBD.criarConexao(this);
        if (conexao != null) {
            movimentacaoRepositorio = new MovimentacaoRepositorio(conexao);
            extrato = movimentacaoRepositorio.buscarTodos(id_usuario);
            //movimentacaoRepositorio.verTabela();
            visualizarLista();
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    private void visualizarLista(){

        MovimentacaoListAdapter arrayAdapter = new MovimentacaoListAdapter(this, extrato);
        lstViewItem.setAdapter(arrayAdapter);

    }
}
