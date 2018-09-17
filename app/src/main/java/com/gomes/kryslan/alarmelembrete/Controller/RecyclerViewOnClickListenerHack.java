package com.gomes.kryslan.alarmelembrete.Controller;

import android.view.View;

public interface RecyclerViewOnClickListenerHack {
    void onClickListener(View view, int position);
    void onLongPressClickListener(View view, int position);
}

/*
Classe reponsável por permitir o Listener de clique nos cards ou nos itens das listas.
*/