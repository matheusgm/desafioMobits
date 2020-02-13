package com.example.desafiomobits;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.desafiomobits.Database.ScriptBD;
import com.example.desafiomobits.Movimentacao.Movimentacao;
import com.example.desafiomobits.Movimentacao.MovimentacaoRepositorio;
import com.example.desafiomobits.Opcoes.DepositoActivity;
import com.example.desafiomobits.Opcoes.ExtratoActivity;
import com.example.desafiomobits.Opcoes.SaqueActivity;
import com.example.desafiomobits.Opcoes.TransferenciaActivity;
import com.example.desafiomobits.Usuario.Perfil;
import com.example.desafiomobits.Usuario.Usuario;
import com.example.desafiomobits.Usuario.UsuarioRepositorio;

public class OpcoesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_saldo;
    private Button btn_extrato;
    private Button btn_saque;
    private Button btn_deposito;
    private Button btn_transferencia;
    private Button btn_visita_gerente;
    private Button btn_trocar_usuario;

    private TextView txt_saldo_oculto;

    private UsuarioRepositorio usuarioRepositorio;
    private MovimentacaoRepositorio movimentacaoRepositorio;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        btn_saldo = findViewById(R.id.btn_saldo);
        btn_extrato = findViewById(R.id.btn_extrato);
        btn_saque = findViewById(R.id.btn_saque);
        btn_deposito = findViewById(R.id.btn_deposito);
        btn_transferencia = findViewById(R.id.btn_transferencia);
        btn_visita_gerente = findViewById(R.id.btn_visita_gerente);
        btn_trocar_usuario = findViewById(R.id.btn_trocar_usuario);

        txt_saldo_oculto = findViewById(R.id.txt_saldo_oculto);

        btn_saldo.setOnClickListener(this);
        btn_extrato.setOnClickListener(this);
        btn_saque.setOnClickListener(this);
        btn_deposito.setOnClickListener(this);
        btn_transferencia.setOnClickListener(this);
        btn_visita_gerente.setOnClickListener(this);
        btn_trocar_usuario.setOnClickListener(this);

        int id =  getIntent().getIntExtra("id_usuario",-1);

        if(id == -1){
            Geral.AlertaNeutro(this,"Erro","Erro ao pegar id do usuario","Sair",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
        }

        SQLiteDatabase conexao = ScriptBD.criarConexao(this);
        if (conexao != null) {
            usuarioRepositorio = new UsuarioRepositorio(conexao);
            movimentacaoRepositorio =  new MovimentacaoRepositorio(conexao);
            usuario = usuarioRepositorio.buscarUsuario(id);
            if(usuario.getPerfil().equals(Perfil.VIP)){
                btn_visita_gerente.setVisibility(View.VISIBLE);

            }
        }
        usuario.verificarSaldoNegativo(movimentacaoRepositorio,usuarioRepositorio);

    }

    @Override
    protected void onResume() {
        super.onResume();
        usuario = usuarioRepositorio.buscarUsuario(usuario.getId());
        usuario.verificarSaldoNegativo(movimentacaoRepositorio,usuarioRepositorio);
        if(!txt_saldo_oculto.getText().equals(getString(R.string.saldo_oculto))){
            txt_saldo_oculto.setText(String.format("R$ %.2f", usuario.getSaldo()));
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if(v.getId() == btn_saldo.getId()){
            float saldo = usuario.getSaldo();
            if(txt_saldo_oculto.getText().equals(getString(R.string.saldo_oculto))){
                txt_saldo_oculto.setText(String.format("R$ %.2f", saldo));
                btn_saldo.setText("Ocultar Saldo");
            }else{
                txt_saldo_oculto.setText(getString(R.string.saldo_oculto));
                btn_saldo.setText(getString(R.string.ver_saldo));
            }
        }else if(v.getId() == btn_deposito.getId()) {
            intent = new Intent(this, DepositoActivity.class);
            System.out.println("Clicando em Deposito!");
        }else if(v.getId() == btn_extrato.getId()) {
            intent = new Intent(this, ExtratoActivity.class);
            System.out.println("Clicando em Extrato!");
        }else if(v.getId() == btn_saque.getId()){
            intent = new Intent(this, SaqueActivity.class);
            System.out.println("Clicando em Saque!");
        }else if(v.getId() == btn_transferencia.getId()){
            intent = new Intent(this, TransferenciaActivity.class);
            System.out.println("Clicando em Transferencia!");
        }else if(v.getId() == btn_visita_gerente.getId()){
            System.out.println("Clicando em Visita Gerente!");
            Geral.AlertaSimNao(this,"Visita do gerente","Deseja solicitar a visita do gerente?", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Movimentacao mov = usuario.sacar(50f,"Visita do Gerente");
                    txt_saldo_oculto.setText(String.format("R$ %.2f", usuario.getSaldo()));
                    usuarioRepositorio.update(usuario);
                    movimentacaoRepositorio.inserir(mov);

                }
            },null);
        }else if(v.getId() == btn_trocar_usuario.getId()){
            System.out.println("Clicando em Trocar Usuario!");
            finish();
        }


        if(intent != null){
            intent.putExtra("id_usuario", usuario.getId());
            startActivity(intent);
        }


    }
}
