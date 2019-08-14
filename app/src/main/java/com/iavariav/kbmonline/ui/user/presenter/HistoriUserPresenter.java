package com.iavariav.kbmonline.ui.user.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.model.PemesananModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.user.adapter.HistoriAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriUserPresenter {
    private ArrayList<PemesananModel> pemesananModels;
    private HistoriAdapter historiAdapter;

    public void getDataHistoriUser(final Context context, String namaPemesan, final RecyclerView recyclerView){
        pemesananModels = new ArrayList<>();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getDataUserHistoriPemesanan("getDataPemesanan", namaPemesan)
                .enqueue(new Callback<ArrayList<PemesananModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PemesananModel>> call, Response<ArrayList<PemesananModel>> response) {
                        if (response.isSuccessful()){
                            pemesananModels = response.body();
                            historiAdapter = new HistoriAdapter(context, pemesananModels);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(historiAdapter);
                            historiAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PemesananModel>> call, Throwable t) {
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
