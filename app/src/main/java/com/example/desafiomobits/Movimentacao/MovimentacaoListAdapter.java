package com.example.desafiomobits.Movimentacao;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.desafiomobits.R;

public class MovimentacaoListAdapter extends BaseAdapter {

    private Context context;
    private List<Movimentacao> movimentacaoList;

    public MovimentacaoListAdapter(Context context, List<Movimentacao> movimentacaoList) {
        this.context = context;
        this.movimentacaoList = movimentacaoList;
    }

    @Override
    public int getCount() {
        return movimentacaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return movimentacaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_list_extrato,null);
        TextView data = v.findViewById(R.id.txt_item_data);
        TextView horario = v.findViewById(R.id.txt_item_horario);
        TextView descricao = v.findViewById(R.id.txt_item_descricao);
        TextView valor = v.findViewById(R.id.txt_item_valor);


        data.setText(movimentacaoList.get(position).getData());
        horario.setText(movimentacaoList.get(position).getHorario());
        descricao.setText(movimentacaoList.get(position).getDescricao());
        float val = movimentacaoList.get(position).getValor();
        if(val < 0){
            valor.setText(String.format("(%.2f)",val));
        }else{
            valor.setText(String.format("%.2f",val));
        }


        v.setTag(movimentacaoList.get(position).getId());

        return v;
    }
}
