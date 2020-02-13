package com.example.desafiomobits.Usuario;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioRepositorio {

    private SQLiteDatabase conexao;
    private String TABELA = "USUARIO";

    public UsuarioRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Usuario usuario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("conta", usuario.getConta());
        contentValues.put("senha", usuario.getSenha());
        contentValues.put("perfil", usuario.getPerfil().getNome());
        contentValues.put("saldo", usuario.getSaldo());
        contentValues.put("data_hora_saldo_negativo", usuario.getData_hora_saldo_negativo());

        conexao.insertOrThrow(TABELA,null,contentValues);
    }

    public void update(Usuario usuario){

        ContentValues values = new ContentValues();
        values.put("id_usuario",usuario.getId());
        values.put("nome",usuario.getNome());
        values.put("conta",usuario.getConta());
        values.put("senha",usuario.getSenha());
        values.put("perfil",usuario.getPerfil().getNome());
        values.put("saldo",usuario.getSaldo());
        values.put("data_hora_saldo_negativo", usuario.getData_hora_saldo_negativo());

        conexao.update(TABELA, values, "id_usuario = "+usuario.getId(), null);
    }

    public void excluir(int id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete(TABELA,"id_usuario = ?",parametros);
    }

    public Usuario buscarUsuario(int id){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" SELECT * FROM %s WHERE id_usuario = %d",TABELA,id));

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            if(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")) == id) {
                Usuario usuario = new Usuario();

                usuario.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                usuario.setConta(resultado.getInt(resultado.getColumnIndexOrThrow("conta")));
                usuario.setSenha(resultado.getInt(resultado.getColumnIndexOrThrow("senha")));
                usuario.setPerfil(resultado.getString(resultado.getColumnIndexOrThrow("perfil")));
                usuario.setSaldo(resultado.getFloat(resultado.getColumnIndexOrThrow("saldo")));
                usuario.setData_hora_saldo_negativo(resultado.getString(resultado.getColumnIndexOrThrow("data_hora_saldo_negativo")));

                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int conta, int senha){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" SELECT * FROM %s WHERE conta = %d AND senha = %d",TABELA,conta, senha));
        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            if(resultado.getInt(resultado.getColumnIndexOrThrow("conta")) == conta && resultado.getInt(resultado.getColumnIndexOrThrow("senha")) == senha ) {
                Usuario usuario = new Usuario();

                usuario.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                usuario.setConta(resultado.getInt(resultado.getColumnIndexOrThrow("conta")));
                usuario.setSenha(resultado.getInt(resultado.getColumnIndexOrThrow("senha")));
                usuario.setPerfil(resultado.getString(resultado.getColumnIndexOrThrow("perfil")));
                usuario.setSaldo(resultado.getFloat(resultado.getColumnIndexOrThrow("saldo")));
                usuario.setData_hora_saldo_negativo(resultado.getString(resultado.getColumnIndexOrThrow("data_hora_saldo_negativo")));

                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioConta(int conta){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" SELECT * FROM %s WHERE conta = %d",TABELA,conta));
        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            if(resultado.getInt(resultado.getColumnIndexOrThrow("conta")) == conta ) {
                Usuario usuario = new Usuario();

                usuario.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                usuario.setConta(resultado.getInt(resultado.getColumnIndexOrThrow("conta")));
                usuario.setSenha(resultado.getInt(resultado.getColumnIndexOrThrow("senha")));
                usuario.setPerfil(resultado.getString(resultado.getColumnIndexOrThrow("perfil")));
                usuario.setSaldo(resultado.getFloat(resultado.getColumnIndexOrThrow("saldo")));
                usuario.setData_hora_saldo_negativo(resultado.getString(resultado.getColumnIndexOrThrow("data_hora_saldo_negativo")));

                return usuario;
            }
        }
        return null;
    }

    public void verTabela(){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM "+TABELA);

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){

            resultado.moveToFirst();

            do{
                System.out.println("");
                System.out.print(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("conta")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("senha")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("perfil")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("saldo")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("data_hora_saldo_negativo")));
                System.out.println("");

            }while(resultado.moveToNext());

        }
    }

}
