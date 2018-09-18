package com.gomes.kryslan.alarmelembrete.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gomes.kryslan.alarmelembrete.Controller.RecyclerViewOnClickListenerHack;
import com.gomes.kryslan.alarmelembrete.Model.Alarmes;
import com.gomes.kryslan.alarmelembrete.R;
import com.gomes.kryslan.alarmelembrete.View.Adapters.ListaAlarmesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.gomes.kryslan.alarmelembrete.Model.BD.BdLite.SelectAlarmesHoje;

public class FragmentAlarmes extends Fragment implements RecyclerViewOnClickListenerHack {
    private Context c;
    private RecyclerView mRecyclerView;
    private List<Alarmes> mList; //filterList = new ArrayList<>();
    //private String queryPesquisa;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*if (savedInstanceState == null) {
            mList = GetAreaEmitenteBd();  //Se for maior do que a lista, começa a repetir os itens. Mas não da erro.
        } else {
            mList = savedInstanceState.getParcelableArrayList("mList");
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_alarmes, container, false);  //pegando o XML do fragment.

        c = view.getContext();

        mRecyclerView = view.findViewById(R.id.rv_list);  //Pegando o recyclerView.
        mRecyclerView.setHasFixedSize(true);  //Vai garantir que o recyclerView não mude de tamanho.

        //LISTENER SCROLL
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*if (dy > 0){  //Quando rola o recyclerView para baixo
                }else if (dy < 0) {}  //Quando rola o recyclerView para cima*/
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));  //Chama o Listener de Click em cada Card, (configuração continua na classe do Adapter).

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);  //Define que o layout da lista será na vertical.
        mRecyclerView.setLayoutManager(lm);

        mList = PegaEventosDaAgenda();

        //ADAPTER
        ListaAlarmesAdapter adapter = new ListaAlarmesAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);  //Pega o parâmetro passado em PraticasAdapter para o clique na lista.
        mRecyclerView.setAdapter(adapter);

        //FLOATING SEARCHVIEW
        /*mSearchView = view.findViewById(R.id.floating_search_view);
        mSearchView.setSearchHint("Pesquisa de Área Emitente...");
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {  //Ao mudar o texto na barra de pesquisa.
                queryPesquisa = newQuery;
                filterList.clear();
                if(TextUtils.isEmpty(newQuery)){  //Se o que for digitado for == " ".
                    filterList.addAll(mList);
                }else{
                    for(int i = 0; i < mList.size(); i++)
                    {
                        if(mList.get(i).getTextoPrincipal().toLowerCase().contains(newQuery)){
                            filterList.add(mList.get(i));
                        }
                    }
                }
                AtualizaTipoLista(filterList);
                //mSearchView.swapSuggestions(newSuggestions); Futuras implementações com Approximate string matching.
            }
        });*/

        //FLOATING ACTION BUTTOM
        /*fabView = view.findViewById(R.id.fabDataNivelAreaAreas);

        alturaFab = Utils.AlturaFabCorrigida(c);
        fabView.setY(alturaFab);  //Corrige altura do FAB
        fabView.setOnRapidFloatingButtonSeparateListener(this);  //Inicia o Listener de clice no FAB.*/

        return view;
    }

//    @Override
//    public void onRFABClick() {
//        GiraFab(fabView);
//
//        //ORGANIZANDO A LISTA ALFABETICAMENTE
//        if(fabView.getRotation() == 0)
//            mList = SelectSubCategoria("areaEmitente", false);
//        else
//            mList = SelectSubCategoria("areaEmitente");
//        //RECRIA A LISTA
//        AtualizaTipoLista(mList);
//    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClickListener(View view, int position) {  //Aqui define o que acontece ao clicar em cada card.

    }

    @Override
    public void onLongPressClickListener(View view, int position) {  //Aqui define o que acontece ao clicar e segurar em cada card.
    }

    public static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{  //Ao clicar nos itens lança um Listener, para fazer a animação.
        private Context mContext;  //Pega várias informações do app no tempo de execução para usar essas informações.
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        private RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvhack){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvhack;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){  //É chamado em onInterceptTouchEvent.
                //--- MotionEvent pode identificar vários tipos de cliques diferentes. ---
                @Override
                public void onLongPress(MotionEvent e){
                    super.onLongPress(e);

                    View viewSelecionada = rv.findChildViewUnder(e.getX(), e.getY());  //Vai identificar a posição do clique (mas retornará o posicionamento abaixo do clicado).

                    if (viewSelecionada != null && mRecyclerViewOnClickListenerHack != null){  //Só confere se o clique foi real mesmo e se existe a view selecionada para não dar erro.
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(viewSelecionada, rv.getChildAdapterPosition(viewSelecionada));
                    }
                }
                @Override
                public boolean onSingleTapUp(MotionEvent e){
                    View viewSelecionada = rv.findChildViewUnder(e.getX(), e.getY());  //Vai identificar a posição do clique (mas retornará o posicionamento abaixo do clicado).

                    if (viewSelecionada != null && mRecyclerViewOnClickListenerHack != null){  //Só confere se o clique foi real mesmo e se existe a view selecionada para não dar erro.
                        mRecyclerViewOnClickListenerHack.onClickListener(viewSelecionada, rv.getChildAdapterPosition(viewSelecionada));  //Aqui chama "onClickListener"
                    }
                    return (true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);  //Identifica se foi um clique simples ou longPress.
            return false;  //se for true, pega o evento de click disparado do layout root (RelativeLayout no caso).
        }
        @Override public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
        @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState){
        /*super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("mList", (ArrayList<TelaInicialCards>) mList);
        savedInstanceState.putString("pesquisa", queryPesquisa);*/
    }

    private void DialogTipoLista(Context contextLocal){
//        new MaterialDialog.Builder(contextLocal)
//                .title(R.string.selecioneTipoLista)
//                .items(R.array.tipoLista)
//                .itemsCallback(new MaterialDialog.ListCallback() {
//                    @Override
//                    public void onSelection(MaterialDialog dialog, View view, int itemSelecionado, CharSequence text) {
//                        BdLite.atualizaTipoLista(itemSelecionado);
//                        AtualizaTipoLista(mList);
//                    }
//                })
//                .show();
    }

    private List<Alarmes> PegaEventosDaAgenda() {
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(c, "Conceda permição para ler o calendário para ativar a integração.", Toast.LENGTH_LONG).show();
            //System.exit(1);  //Fecha o app IMPLEMENTAR *************
            //return;
        }

        //PEGA INFORMAÇÕES DO CALENDÁRIO
        String[] colunas = new String[] {
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,  //Data que o evento começa.
                CalendarContract.Events.DTEND,  //Data que o evento termina.
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.DELETED,
                CalendarContract.Events.EVENT_LOCATION};
        //O mês começa no 0 então: 0 = Janeiro, 1 = Fevereiro, etc...

        //Define o dia que começa a pegar as informações:
        Date hoje = Calendar.getInstance().getTime();
        Locale local = getResources().getConfiguration().locale;  //Pega o local para definir o tempo corretamente baseado no fuso horário.
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy", local);
        int ano = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("MM", local);
        int mes = Integer.parseInt(dataFormat.format(hoje));
        dataFormat = new SimpleDateFormat("dd", local);
        int dia = Integer.parseInt(dataFormat.format(hoje));

        //DEFINE O RANGE DE DATA COMO APENAS HOJE:
        Calendar dataComeco = Calendar.getInstance();
        dataComeco.set(ano,mes-1,dia,0,0);  //(Mês é -1 pois o mês na tabela começa no 0).

        Calendar dataFim = Calendar.getInstance();
        dataFim.set(ano,mes-1,dia,23,59);  //(Mês é -1 pois o mês na tabela começa no 0).

        String where = "(( " + CalendarContract.Events.DTSTART + " >= " + dataComeco.getTimeInMillis() +
                " ) AND ( " +  //where data do evento seja hoje.
                CalendarContract.Events.DTSTART + " <= " + dataFim.getTimeInMillis() + " ))";

        Cursor cursor = c.getContentResolver().query(CalendarContract.Events.CONTENT_URI, colunas, where, null, null );

        List<Alarmes> list = new ArrayList<>();
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH", local);
        SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm", local);
        if (cursor.moveToFirst()){
            do {
                if(cursor.getInt(6) == 0) {  //Confere se o valor foi deletado, se sim (0) adiciona, não == 1.
                    Alarmes a = new Alarmes();
                    //DATA COMEÇA EVENTO
                    long eventStart = cursor.getLong(3);
                    Calendar calendario = Calendar.getInstance();
                    calendario.setTimeInMillis(eventStart);

                    a.setHora(Integer.valueOf(formatoHora.format(calendario.getTime())));
                    a.setMinuto(Integer.valueOf(formatoMinuto.format(calendario.getTime())));

                    //CONFERE SE É ANIVERSÁRIO (futuramente pode configurar para criar um evento no dia seguinte para lembrar)
                    a.setLembrete(cursor.getString(1));
                    a.setAtivado(1);
                    list.add(a);
                }
            } while (cursor.moveToNext());
        }
        return list;
    }
}
