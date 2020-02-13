package com.example.desafiomobits.Movimentacao;

public class Movimentacao {

    private int id;
    private int id_usuario;
    private String data;
    private String horario;
    private String descricao;
    private float valor;

    public Movimentacao(){

    }

    public Movimentacao(int id_usuario, String data, String horario, String descricao, float valor) {
        this.id_usuario = id_usuario;
        this.data = data;
        this.horario = horario;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
