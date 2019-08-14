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
import com.iavariav.kbmonline.model.PemesananModel;

import java.util.ArrayList;

public class AtasanHistoriAprovalAdapter extends RecyclerView.Adapter<AtasanHistoriAprovalAdapter.ViewHolder> {
    private String id;
    private Context context;

    private ArrayList<PemesananModel> PemesananModels;

    public AtasanHistoriAprovalAdapter(Context context, ArrayList<PemesananModel> PemesananModels) {
        this.context = context;
        this.PemesananModels = PemesananModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_histori, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(Config.SHARED_PREF_ID, "");
        holder.tvRegToken.setText(PemesananModels.get(position).getREGTOKENPEMESANAN());
        holder.tvNama.setText(PemesananModels.get(position).getNAMAPEMESAN());
        holder.tvJenisKeperluan.setText(PemesananModels.get(position).getJENISKEPERLUAN());
        holder.tvJenisPemesanan.setText(PemesananModels.get(position).getJENISPEMESANAN());
        holder.tvKm.setText(PemesananModels.get(position).getJARAKPERKM() + "\n KM");
        holder.tvKeberangkatan.setText(PemesananModels.get(position).getKEBERANGKATANAREAPOOL());
        holder.tvWaktuKeberangkatan.setText(PemesananModels.get(position).getWAKTUKEBERANGKATAN());
        holder.tvTujuan.setText(PemesananModels.get(position).getTUJUANALAMATJEMPUT());
        holder.tvWaktuTujuan.setText(PemesananModels.get(position).getWAKTUKEPULANGAN());
        holder.tvIsiPenumpang.setText(PemesananModels.get(position).getISIPENUMPANG());
        holder.tvKeternangan.setText(PemesananModels.get(position).getKETERANGAN());
        holder.tvStatus.setText(PemesananModels.get(position).getSTATUSPEMESANAN());


        String jarakKm = PemesananModels.get(position).getJARAKPERKM();
//        double hitungLiter = Integer.parseInt(jarakKm)/ 11.6;
//        double hitugHargaBBM = hitungLiter * Integer.parseInt(PemesananModels.get(position).getBENSINPERLITER());

        holder.tvHargaBbm.setText("RP." + PemesananModels.get(position).getBENSINPERLITER());


    }


    @Override
    public int getItemCount() {
        return PemesananModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRegToken;
        private TextView tvNama;
        private TextView tvJenisKeperluan;
        private TextView tvJenisPemesanan;
        private TextView tvKm;
        private TextView tvKeberangkatan;
        private TextView tvWaktuKeberangkatan;
        private TextView tvTujuan;
        private TextView tvWaktuTujuan;
        private TextView tvIsiPenumpang;
        private TextView tvKeternangan;
        private TextView tvHargaBbm;
        private TextView tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRegToken = itemView.findViewById(R.id.tv_reg_token);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvJenisKeperluan = itemView.findViewById(R.id.tv_jenis_keperluan);
            tvJenisPemesanan = itemView.findViewById(R.id.tv_jenis_pemesanan);
            tvKm = itemView.findViewById(R.id.tv_km);
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan);
            tvWaktuKeberangkatan = itemView.findViewById(R.id.tv_waktu_keberangkatan);
            tvTujuan = itemView.findViewById(R.id.tv_tujuan);
            tvWaktuTujuan = itemView.findViewById(R.id.tv_waktu_tujuan);
            tvIsiPenumpang = itemView.findViewById(R.id.tv_isi_penumpang);
            tvKeternangan = itemView.findViewById(R.id.tv_keternangan);
            tvHargaBbm = itemView.findViewById(R.id.tv_harga_bbm);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
