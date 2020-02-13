package com.example.desafiomobits.Opcoes;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.desafiomobits.Database.ScriptBD;
import com.example.desafiomobits.Geral;
import com.example.desafiomobits.Movimentacao.Movimentacao;
import com.example.desafiomobits.Movimentacao.MovimentacaoRepositorio;
import com.example.desafiomobits.R;
import com.example.desafiomobits.Usuario.Perfil;
import com.example.desafiomobits.Usuario.Usuario;
import com.example.desafiomobits.Usuario.UsuarioRepositorio;

public class TransferenciaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_transferir;
    private EditText editText_conta_transferencia;
    private EditText editText_valor_transferencia;

    private UsuarioRepositorio usuarioRepositorio;
    private MovimentacaoRepositorio movimentacaoRepositorio;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferencia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_transferir = findViewById(R.id.btn_transferir);
        editText_conta_transferencia = findViewById(R.id.editText_conta_transferencia);
        editText_valor_transferencia = findViewById(R.id.editText_valor_transferencia);

        btn_transferir.setOnClickListener(this);

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
            usuarioRepositorio = new UsuarioRepositorio(conexao);
            movimentacaoRepositorio = new MovimentacaoRepositorio(conexao);
            usuario = usuarioRepositorio.buscarUsuario(id_usuario);
            //usuarioRepositorio.verTabela();
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {

        String texto_conta = editText_conta_transferencia.getText().toString();
        String texto_valor = editText_valor_transferencia.getText().toString();
        if(!texto_conta.matches("") && !texto_valor.matches("")){
            int conta = Integer.parseInt(texto_conta);
            float valor = Float.parseFloat(texto_valor);

            if(conta == usuario.getConta()){
                Toast.makeText(this, "Impossivel realizar uma transferencia para si proprio!", Toast.LENGTH_LONG).show();
                return;
            }

            if(usuario.getSaldo()-valor < 0 && usuario.getPerfil().equals(Perfil.NORMAL)){
                Toast.makeText(this, "Impossivel realizar a transferencia. Saldo insuficiente!", Toast.LENGTH_LONG).show();
                return;
            }

            if(valor > 1000 && usuario.getPerfil().equals(Perfil.NORMAL)){
                Toast.makeText(this, "Impossivel realizar a transferencia. Valor acima do limite permitido!", Toast.LENGTH_LONG).show();
                return;
            }

            Usuario destinario = usuarioRepositorio.buscarUsuarioConta(conta);
            if(destinario == null){
                Toast.makeText(this, "Conta não encontrada no banco de dados!", Toast.LENGTH_LONG).show();
                return;
            }

            Movimentacao mov_pagador = usuario.sacar(valor,"Transferencia enviada para a conta "+conta);
            Movimentacao mov_destinario = destinario.depositar(valor,"Transferencia recebida da conta "+usuario.getConta());
            movimentacaoRepositorio.inserir(mov_pagador);
            movimentacaoRepositorio.inserir(mov_destinario);

            Movimentacao mov;
            if(usuario.getPerfil().equals(Perfil.NORMAL)){
                mov = usuario.sacar(8f,"Taxa de transferencia");
            }else{
                float taxa = valor*(float)0.008;
                mov = usuario.sacar(taxa,"Taxa de transferencia");
            }
            movimentacaoRepositorio.inserir(mov);
            usuarioRepositorio.update(usuario);
            usuarioRepositorio.update(destinario);
            Toast.makeText(this, String.format("Transferencia de R$ %.2f realizada com sucesso!",valor), Toast.LENGTH_LONG).show();
            finish();

        }else{
            Geral.AlertaNeutro(this,"Campo em branco!","Campo da conta ou do valor está vazio!","Tente Novamente");
        }

    }
}
