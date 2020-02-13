package com.example.desafiomobits.Usuario;

import com.example.desafiomobits.Geral;
import com.example.desafiomobits.Movimentacao.Movimentacao;
import com.example.desafiomobits.Movimentacao.MovimentacaoRepositorio;
import com.example.desafiomobits.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Usuario {

    private int id;
    private String nome;
    private int conta;
    private int senha;
    private Perfil perfil;
    private float saldo;
    private String data_hora_saldo_negativo;

    public Usuario(){

    }

    public Usuario(String nome, int conta, int senha, Perfil perfil) {
        this.nome = nome;
        this.conta = conta;
        this.senha = senha;
        this.perfil = perfil;
        this.saldo = 0f;
        this.data_hora_saldo_negativo = null;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        if(perfil.equals(Perfil.NORMAL.getNome())){
            this.perfil = Perfil.NORMAL;
        }else if(perfil.equals(Perfil.VIP.getNome())){
            this.perfil = Perfil.VIP;
        }

    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getData_hora_saldo_negativo() {
        return data_hora_saldo_negativo;
    }

    public void setData_hora_saldo_negativo(String data_hora_saldo_negativo) {
        this.data_hora_saldo_negativo = data_hora_saldo_negativo;
    }

    public Movimentacao sacar(float valorSacado, String motivo){
        Movimentacao mov = new Movimentacao();
        mov.setValor(-valorSacado);

        String data = Geral.dataAtual();
        String horario = Geral.horarioAtual();
//        System.out.println(data);
//        System.out.println(horario);

        mov.setHorario(horario);
        mov.setData(data);
        mov.setId_usuario(this.getId());
        mov.setDescricao(motivo);

        this.setSaldo(this.getSaldo() - valorSacado);

        if(this.perfil.equals(Perfil.VIP)){
            if(this.getSaldo() < 0 && this.getData_hora_saldo_negativo() == null){
                this.setData_hora_saldo_negativo(Geral.data_horaAtual());
            }
        }

        return mov;

    }

    public Movimentacao depositar(float valorDepositado, String motivo){
        Movimentacao mov = new Movimentacao();
        mov.setValor(valorDepositado);

        String data = Geral.dataAtual();
        String horario = Geral.horarioAtual();
//        System.out.println(data);
//        System.out.println(horario);

        mov.setHorario(horario);
        mov.setData(data);
        mov.setId_usuario(this.getId());
        mov.setDescricao(motivo);
        this.setSaldo(this.getSaldo() + valorDepositado);

        if(this.perfil.equals(Perfil.VIP)){
            if(this.getSaldo() >= 0 && this.getData_hora_saldo_negativo() != null){
                this.setData_hora_saldo_negativo(null);
            }
        }

        return mov;
    }

    public void verificarSaldoNegativo(MovimentacaoRepositorio movimentacaoRepositorio,UsuarioRepositorio usuarioRepositorio){
        String data_hora_agora = Geral.data_horaAtual();
        if(this.getData_hora_saldo_negativo()!=null){
            long diff = -1;
            long diffInMillies = -1;
            Date date1 = null;
            Date date2 = null;
            try {
                date1=new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(this.getData_hora_saldo_negativo());
                date2=new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data_hora_agora);

                diffInMillies = Math.abs(date2.getTime() - date1.getTime());
                diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            System.out.println(diff);

//            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");

            for(int i =1;i<=diff;i++){
                float taxa = this.getSaldo()*(float)0.001;
                this.setSaldo(this.getSaldo()-taxa);

//                int minuto_em_milli = i*60*1000;
//                Date data = new Date(date1.getTime() + minuto_em_milli);
//                String data_final = formatadorData.format(data.getTime());
//                String horario_final = formatadorHora.format(data.getTime());
//
//                movimentacaoRepositorio.inserir(new Movimentacao(this.getId(),data_final,horario_final,"Taxa Saldo Negativo",taxa));
            }

            this.setData_hora_saldo_negativo(data_hora_agora);
            usuarioRepositorio.update(this);
        }
    }


}
