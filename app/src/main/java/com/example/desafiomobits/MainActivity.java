package com.example.desafiomobits;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desafiomobits.Database.DadosOpenHelper;
import com.example.desafiomobits.Database.ScriptBD;
import com.example.desafiomobits.Usuario.Perfil;
import com.example.desafiomobits.Usuario.Usuario;
import com.example.desafiomobits.Usuario.UsuarioRepositorio;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText conta;
    private EditText senha;

    private UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);

        conta = findViewById(R.id.editText_conta);
        senha = findViewById(R.id.editText_senha);

        btnLogin.setOnClickListener(this);


        SQLiteDatabase conexao = ScriptBD.criarConexao(this);
        if (conexao != null) {
            usuarioRepositorio = new UsuarioRepositorio(conexao);

            if (usuarioRepositorio.buscarUsuario(11111, 1234) == null && usuarioRepositorio.buscarUsuario(22222, 1234) == null) {
                Usuario usu1 = new Usuario("nome 1", 11111, 1234, Perfil.NORMAL);
                Usuario usu2 = new Usuario("nome 2", 22222, 1234, Perfil.VIP);
                usuarioRepositorio.inserir(usu1);
                usuarioRepositorio.inserir(usu2);
            }

            usuarioRepositorio.verTabela();
        }

    }

    @Override
    public void onClick(View v) {

        if(!conta.getText().toString().matches("") && !senha.getText().toString().matches("")) {

            int id = idCredenciais(Integer.parseInt(conta.getText().toString()), Integer.parseInt(senha.getText().toString()));

            if (id == -1) {
                Geral.AlertaNeutro(this,"Credenciais Incorretas","Número da conta ou senha estão incorretos!","Tentar Novamente");
            } else {
                Intent intent = new Intent(this, OpcoesActivity.class);
                intent.putExtra("id_usuario", id);
                conta.setText("");
                senha.setText("");
                startActivity(intent);
            }
        }else{
            Geral.AlertaNeutro(this,"Credenciais Incorretas","Campos em branco!","Tentar Novamente");
        }
//        Intent intent = new Intent(this, OpcoesActivity.class);
//        intent.putExtra("id_usuario", 2);
//        startActivity(intent);

    }

    private int idCredenciais(int conta, int senha) {
        Usuario usuario = usuarioRepositorio.buscarUsuario(conta,senha);
        if(usuario==null){
            return -1;
        }
        return usuario.getId();
    }


}
