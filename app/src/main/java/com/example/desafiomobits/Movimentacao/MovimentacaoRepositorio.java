package com.example.desafiomobits.Movimentacao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MovimentacaoRepositorio {
    private SQLiteDatabase conexao;
    private String TABELA = "MOVIMENTACAO";

    public MovimentacaoRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Movimentacao movimentacao){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_usuario", movimentacao.getId_usuario());
        contentValues.put("data", movimentacao.getData());
        contentValues.put("horario", movimentacao.getHorario());
        contentValues.put("descricao", movimentacao.getDescricao());
        contentValues.put("valor", movimentacao.getValor());

        conexao.insertOrThrow(TABELA,null,contentValues);
    }

    public void update(ContentValues values, int id){
        conexao.update(TABELA, values, "id_movimentacao = "+id, null);
    }

    public void excluir(int id) {
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete(TABELA,"id_movimentacao = ?",parametros);
    }

    public Movimentacao buscarMovimentacao(int id){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" SELECT * FROM %s WHERE id_movimentacao = %d",TABELA,id));

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            if(resultado.getInt(resultado.getColumnIndexOrThrow("id_movimentacao")) == id) {
                Movimentacao movimentacao = new Movimentacao();

                movimentacao.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id_movimentacao")));
                movimentacao.setId_usuario(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                movimentacao.setData(resultado.getString(resultado.getColumnIndexOrThrow("data")));
                movimentacao.setHorario(resultado.getString(resultado.getColumnIndexOrThrow("horario")));
                movimentacao.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
                movimentacao.setValor(resultado.getFloat(resultado.getColumnIndexOrThrow("valor")));

                return movimentacao;
            }
        }
        return null;
    }

    public ArrayList<Movimentacao> buscarTodos(int id_usuario){

        ArrayList<Movimentacao> extrato = new ArrayList<Movimentacao>();

        StringBuilder sql = new StringBuilder();
        sql.append(String.format(" SELECT * FROM %s WHERE id_usuario = %d",TABELA,id_usuario));

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do{
                Movimentacao movimentacao = new Movimentacao();

                movimentacao.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id_movimentacao")));
                movimentacao.setId_usuario(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                movimentacao.setData(resultado.getString(resultado.getColumnIndexOrThrow("data")));
                movimentacao.setHorario(resultado.getString(resultado.getColumnIndexOrThrow("horario")));
                movimentacao.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
                movimentacao.setValor(resultado.getFloat(resultado.getColumnIndexOrThrow("valor")));

                extrato.add(movimentacao);

            }while(resultado.moveToNext());

        }
        return extrato;
    }

    public void verTabela(){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM "+TABELA);

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){

            resultado.moveToFirst();

            do{
                System.out.println("");
                System.out.print(resultado.getInt(resultado.getColumnIndexOrThrow("id_movimentacao")));
                System.out.print(" ");
                System.out.print(resultado.getInt(resultado.getColumnIndexOrThrow("id_usuario")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("data")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("horario")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
                System.out.print(" ");
                System.out.print(resultado.getString(resultado.getColumnIndexOrThrow("valor")));
                System.out.println("");

            }while(resultado.moveToNext());

        }
    }
}
