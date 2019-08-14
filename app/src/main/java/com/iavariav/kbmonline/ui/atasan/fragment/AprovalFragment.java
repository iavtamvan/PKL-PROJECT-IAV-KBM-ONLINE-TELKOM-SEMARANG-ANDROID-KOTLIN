package com.iavariav.kbmonline.ui.atasan.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.model.PemesananModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.atasan.adapter.AtasanAprovalAdapter;
import com.iavariav.kbmonline.ui.atasan.presenter.AprovalPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AprovalFragment extends Fragment {
    private RecyclerView rv;

    private AprovalPresenter aprovalPresenter;


    public AprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_aproval, container, false);
        initView(view);
        aprovalPresenter = new AprovalPresenter();
        aprovalPresenter.getDatas(getActivity(), rv);
        return view;
    }

    private void initView(View View) {
        rv = View.findViewById(R.id.rv);
    }
}
