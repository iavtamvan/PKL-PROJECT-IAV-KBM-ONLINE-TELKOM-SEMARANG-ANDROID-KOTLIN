package com.iavariav.kbmonline.ui.atasan.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.model.PemesananModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.atasan.adapter.AtasanHistoriAprovalAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriAtasanFragment extends Fragment {
    private RecyclerView rv;
    private AtasanHistoriAprovalAdapter atasanHistoriAprovalAdapter;
    private ArrayList<PemesananModel> PemesananModels;

    private String idUser;

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

        PemesananModels = new ArrayList<>();
        getDatas();

        return view;
    }

    private void getDatas() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getDataHistoriAtasan("getHistoriAtasan", idUser)
                .enqueue(new Callback<ArrayList<PemesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PemesananModel>> call, Response<ArrayList<PemesananModel>> response) {
                        if (response.isSuccessful()){
                            PemesananModels = response.body();
                            atasanHistoriAprovalAdapter = new AtasanHistoriAprovalAdapter(getActivity(), PemesananModels);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv. setAdapter(atasanHistoriAprovalAdapter);
                            atasanHistoriAprovalAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PemesananModel>> call, Throwable t) {
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
    }
}
