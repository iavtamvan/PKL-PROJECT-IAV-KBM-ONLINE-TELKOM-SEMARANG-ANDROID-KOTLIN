package com.iavariav.kbmonline.ui.user.fragment;


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
import com.iavariav.kbmonline.ui.user.presenter.HistoriUserPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriUserFragment extends Fragment {


    private RecyclerView rv;
    private HistoriUserPresenter historiUserPresenter;
    private LinearLayout div;

    public HistoriUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_histori_user, container, false);
        initView(view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.INSTANCE.getSHARED_PREF_NAME(), Context.MODE_PRIVATE);
        String namaLengkap = sharedPreferences.getString(Config.INSTANCE.getSHARED_PREF_NAMA_LENGKAP(), "");
//        Toast.makeText(getActivity(), "" + namaLengkap, Toast.LENGTH_SHORT).show();
        historiUserPresenter = new HistoriUserPresenter();
        historiUserPresenter.getDataHistoriUser(getActivity(), namaLengkap, rv, div);

        return view;
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
        div = view.findViewById(R.id.div);
    }
}
