package com.example.desafiomobits.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.desafiomobits.Geral;

public class ScriptBD {

    public static String getCreateTableUsuario() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS USUARIO ( ");
        sql.append(" id_usuario INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append(" nome VARCHAR (200), ");
        sql.append(" conta INTEGER, ");
        sql.append(" senha INTEGER, ");
        sql.append(" perfil VARCHAR (15), ");
        sql.append(" saldo FLOAT, ");
        sql.append(" data_hora_saldo_negativo VARCHER(16) ) ");

        return sql.toString();
    }

    public static String getCreateTableMovimentacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS MOVIMENTACAO ( ");
        sql.append(" id_movimentacao INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append(" id_usuario INTEGER NOT NULL, ");
        sql.append(" data VARCHAR (10), ");
        sql.append(" horario VARCHAR (5), ");
        sql.append(" descricao VARCHAR (200), ");
        sql.append(" valor FLOAT ) ");

        return sql.toString();
    }

    public static SQLiteDatabase criarConexao(Context contexto){
        try{
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(contexto);

            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            return conexao;

        }catch(SQLException ex){
            Geral.AlertaNeutro(contexto,"Erro ao criar banco no "+contexto.getClass().getName(),ex.getMessage(),"OK");
        }
        return null;
    }




}
