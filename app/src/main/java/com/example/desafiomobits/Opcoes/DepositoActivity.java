package com.example.desafiomobits.Opcoes;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DepositoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_depositar;
    private EditText editText_valorDeposito;
    private TextView txt_saldo_deposito;

    private UsuarioRepositorio usuarioRepositorio;
    private MovimentacaoRepositorio movimentacaoRepositorio;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposito);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_depositar = findViewById(R.id.btn_depositar);
        editText_valorDeposito = findViewById(R.id.editText_valorDeposito);
        txt_saldo_deposito = findViewById(R.id.txt_saldo_deposito);
        txt_saldo_deposito.setText("");

        btn_depositar.setOnClickListener(this);

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
            txt_saldo_deposito.setText(String.format("%.2f",usuario.getSaldo()));
            //usuarioRepositorio.verTabela();
        }
        usuario.verificarSaldoNegativo(movimentacaoRepositorio,usuarioRepositorio);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {

        String texto_valor_deposito = editText_valorDeposito.getText().toString();
        if(!texto_valor_deposito.matches("")){
            float valorDeposito = Float.parseFloat(texto_valor_deposito);
            Movimentacao mov = usuario.depositar(valorDeposito,"Deposito");

            txt_saldo_deposito.setText(String.format("%.2f",usuario.getSaldo()));
            movimentacaoRepositorio.inserir(mov);
            usuarioRepositorio.update(usuario);
            Toast.makeText(this, String.format("Deposito de R$%.2f realizado com sucesso!",valorDeposito), Toast.LENGTH_LONG).show();
            finish();
        }else{
            Geral.AlertaNeutro(this,"Campo em branco!","Campo do valor a ser depositado está vazio!","Tente Novamente");
        }

    }
}
