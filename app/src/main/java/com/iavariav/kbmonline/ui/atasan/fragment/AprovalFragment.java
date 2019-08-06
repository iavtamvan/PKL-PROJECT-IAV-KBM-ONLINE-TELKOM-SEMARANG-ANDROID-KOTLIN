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
import com.iavariav.kbmonline.model.AtasanAprovalModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.atasan.adapter.AtasanAprovalAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AprovalFragment extends Fragment {
    private RecyclerView rv;

    private AtasanAprovalAdapter atasanAprovalAdapter;
    private ArrayList<AtasanAprovalModel> atasanAprovalModels;


    public AprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_aproval, container, false);
        initView(view);

        atasanAprovalModels = new ArrayList<>();
        getData();

        return view;
    }

    private void getData() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getAllData("getAllDataPemesanan")
                .enqueue(new Callback<ArrayList<AtasanAprovalModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AtasanAprovalModel>> call, Response<ArrayList<AtasanAprovalModel>> response) {
                        if (response.isSuccessful()){
                            atasanAprovalModels = response.body();
                            atasanAprovalAdapter = new AtasanAprovalAdapter(getActivity(), atasanAprovalModels);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv.setAdapter(atasanAprovalAdapter);
                            atasanAprovalAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AtasanAprovalModel>> call, Throwable t) {

                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView(View View) {
        rv = View.findViewById(R.id.rv);
    }
}
