package com.gomes.kryslan.alarmelembrete.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.gomes.kryslan.alarmelembrete.Controller.Tools;
import com.gomes.kryslan.alarmelembrete.R;

public class TelaDespertador extends Tools {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_layout);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }
}
