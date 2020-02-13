package com.example.desafiomobits.Usuario;

public enum Perfil {
    NORMAL("Normal"), VIP("Vip");

    private String nome;
    Perfil(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}
