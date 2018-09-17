package com.gomes.kryslan.alarmelembrete.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.gomes.kryslan.alarmelembrete.Controller.RecyclerViewOnClickListenerHack;
import com.gomes.kryslan.alarmelembrete.Model.Alarmes;
import com.gomes.kryslan.alarmelembrete.R;
import com.turingtechnologies.materialscrollbar.ICustomAdapter;

import java.util.List;

public class ListaAlarmesAdapter extends RecyclerView.Adapter<ListaAlarmesAdapter.MyViewHolder> implements ICustomAdapter {
    private List<Alarmes> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    //CONSTRUTOR
    public ListaAlarmesAdapter(Context c, List<Alarmes> lista){
        mList = lista;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ListaAlarmesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {   //só é chamado na hora de criar uma nova view.
        View v = mLayoutInflater.inflate(R.layout.adapter_alarmes, viewGroup, false);

        return new ListaAlarmesAdapter.MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{  //Responsável por controlar os cardViews, deletando os que não aparecem na tela para preservar memória
        public TextView hora;
        public TextView minuto;
        public TextView lembrete;
        public Switch ativado;

        private MyViewHolder(View itemView){
            super(itemView);

            hora = itemView.findViewById(R.id.hora);
            minuto = itemView.findViewById(R.id.minuto);
            lembrete = itemView.findViewById(R.id.lembrete);
            ativado = itemView.findViewById(R.id.ativado);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getLayoutPosition());
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAlarmesAdapter.MyViewHolder myViewHolder, int positionList) {  //Vincula nossos dados com a view. (Seta o valor de cada 'mList' com uma view)
        myViewHolder.hora.setText(mList.get(positionList).getHora());
        myViewHolder.minuto.setText(mList.get(positionList).getMinuto());
        myViewHolder.lembrete.setText(mList.get(positionList).getLembrete());
        myViewHolder.ativado.setText(mList.get(positionList).isAtivado());
    }

    @Override
    public int getItemCount() {  //Retorna a quantidade da lista.
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;  //Está adicionando o parâmetro de clique de entrada ao hack para ativar o click.
    }

    @Override
    public String getCustomStringForElement(int element) {
        return String.valueOf(mList.get(element).getHora());
    }
}
