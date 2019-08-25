package com.iavariav.kbmonline.ui.atasan.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.ui.atasan.presenter.HistoriAtasanPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriAtasanFragment extends Fragment {
    private RecyclerView rv;
    private String idUser;

    private HistoriAtasanPresenter historiAtasanPresenter;
    private LinearLayout div;

    public HistoriAtasanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_histori_atasan, container, false);
        initView(view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString(Config.SHARED_PREF_ID, "");

        historiAtasanPresenter = new HistoriAtasanPresenter();
        historiAtasanPresenter.getDatas(getActivity(), idUser, rv, div);

        return view;
    }


    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
        div = view.findViewById(R.id.div);
    }
}
