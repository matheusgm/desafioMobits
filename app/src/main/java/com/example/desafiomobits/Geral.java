package com.example.desafiomobits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Geral {

    public static void AlertaNeutro(Context context, String titulo, String msg, String msg_botao, DialogInterface.OnClickListener listener){
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(titulo);
        dlg.setMessage(msg);
        dlg.setNeutralButton(msg_botao, listener);
        dlg.show();
    }

    public static void AlertaNeutro(Context context, String titulo, String msg, String msg_botao){
        AlertaNeutro(context,titulo,msg,msg_botao,null);
    }

    public static void AlertaSimNao(Context context, String titulo, String msg, DialogInterface.OnClickListener listenerPositivo,DialogInterface.OnClickListener listenerNegativo){
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(titulo);
        dlg.setMessage(msg);
        dlg.setPositiveButton("Sim",listenerPositivo);
        dlg.setNegativeButton("Não",listenerNegativo);
        dlg.show();
    }


    public static String dataAtual(){
        Calendar calendar =  Calendar.getInstance();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-3");
        formatador.setTimeZone(timeZone);

        return formatador.format(calendar.getTime());
    }

    public static String horarioAtual(){
        Calendar calendar =  Calendar.getInstance();

        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-3");
        formatador.setTimeZone(timeZone);

        return formatador.format(calendar.getTime());
    }

    public static String data_horaAtual(){
        String data = Geral.dataAtual();
        String hora = Geral.horarioAtual();
        return data+" "+hora;
    }

}
