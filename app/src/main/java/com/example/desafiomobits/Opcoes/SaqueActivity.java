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

public class SaqueActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_sacar;
    private EditText editText_valorSaque;
    private TextView txt_saldo;

    private UsuarioRepositorio usuarioRepositorio;
    private MovimentacaoRepositorio movimentacaoRepositorio;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saque);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_sacar = findViewById(R.id.btn_sacar);
        editText_valorSaque = findViewById(R.id.editText_valorSaque);
        txt_saldo = findViewById(R.id.txt_saldo_saque);
        txt_saldo.setText("");

        btn_sacar.setOnClickListener(this);

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
            txt_saldo.setText(String.format("%.2f",usuario.getSaldo()));
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

        String texto_valor_sacado = editText_valorSaque.getText().toString();
        if(!texto_valor_sacado.matches("")){
            float valorSacado = Float.parseFloat(texto_valor_sacado);
            if(usuario.getSaldo() - valorSacado < 0 && usuario.getPerfil().equals(Perfil.NORMAL)){

                System.out.println("Não é possivel sacar esse valor! Saldo Insuficiente!");
                Toast.makeText(this, "Não é possivel sacar esse valor! Saldo Insuficiente!", Toast.LENGTH_LONG).show();
                return;

            }
            Movimentacao mov = usuario.sacar(valorSacado,"Saque");
            txt_saldo.setText(String.format("%.2f",usuario.getSaldo()));
            movimentacaoRepositorio.inserir(mov);
            usuarioRepositorio.update(usuario);
            Toast.makeText(this, String.format("Saque de R$%.2f realizado com sucesso!",valorSacado), Toast.LENGTH_LONG).show();
            finish();
        }else{
            Geral.AlertaNeutro(this,"Campo em branco!","Campo do valor a ser sacado está vazio!","Tente Novamente");
        }

    }

}
