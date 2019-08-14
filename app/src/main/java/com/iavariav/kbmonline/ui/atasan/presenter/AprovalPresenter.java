package com.iavariav.kbmonline.ui.atasan.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.model.PemesananModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.atasan.adapter.AtasanAprovalAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AprovalPresenter {
    private ArrayList<PemesananModel> PemesananModels;
    private AtasanAprovalAdapter atasanAprovalAdapter;

    public void getDatas(final Context context, final RecyclerView rv) {
        PemesananModels = new ArrayList<>();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getAllData("getAllDataPemesanan")
                .enqueue(new Callback<ArrayList<PemesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PemesananModel>> call, Response<ArrayList<PemesananModel>> response) {
                        if (response.isSuccessful()){
                            PemesananModels = response.body();
                            atasanAprovalAdapter = new AtasanAprovalAdapter(context, PemesananModels);
                            rv.setLayoutManager(new LinearLayoutManager(context));
                            rv.setAdapter(atasanAprovalAdapter);
                            atasanAprovalAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PemesananModel>> call, Throwable t) {

                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
