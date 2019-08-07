package com.iavariav.kbmonline.ui.atasan.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.model.MobilModel;

import java.util.ArrayList;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.ViewHolder> {
    private ArrayList<MobilModel> mobilModels;
    private Context context;

    private String id;

    public MobilAdapter(ArrayList<MobilModel> mobilModels, Context context) {
        this.mobilModels = mobilModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mobil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(Config.SHARED_PREF_ID, "");
        holder.tvPlatMobilStatus.setText(mobilModels.get(position).getPLATMOBIL() + " | " + mobilModels.get(position).getSTATUSMOBIL());
        holder.tvJenisMobil.setText(mobilModels.get(position).getJENISMOBIL());
        holder.tvSopirMobil.setText(mobilModels.get(position).getNAMASUPIR());
        holder.tvTypeMobil.setText(mobilModels.get(position).getTYPEMOBIL());
        holder.tvDeskripsiMobil.setText(mobilModels.get(position).getDESKRIPSIMOBIL());

    }


    @Override
    public int getItemCount() {
        return mobilModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPlatMobilStatus;
        private TextView tvJenisMobil;
        private TextView tvTypeMobil;
        private TextView tvSopirMobil;
        private TextView tvDeskripsiMobil;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlatMobilStatus = itemView.findViewById(R.id.tv_plat_mobil_status);
            tvJenisMobil = itemView.findViewById(R.id.tv_jenis_mobil);
            tvTypeMobil = itemView.findViewById(R.id.tv_type_mobil);
            tvSopirMobil = itemView.findViewById(R.id.tv_sopir_mobil);
            tvDeskripsiMobil = itemView.findViewById(R.id.tv_deskripsi_mobil);
        }
    }
}
