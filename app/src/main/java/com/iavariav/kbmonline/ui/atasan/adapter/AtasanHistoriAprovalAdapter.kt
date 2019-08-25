package com.iavariav.kbmonline.ui.atasan.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.model.PemesananModel

import java.util.ArrayList

class AtasanHistoriAprovalAdapter(private val context: Context, private val PemesananModels: ArrayList<PemesananModel>) : RecyclerView.Adapter<AtasanHistoriAprovalAdapter.ViewHolder>() {
    private var id: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_histori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        id = sharedPreferences.getString(Config.SHARED_PREF_ID, "")
        holder.tvRegToken.setText(PemesananModels[position].getREGTOKENPEMESANAN())
        holder.tvNama.setText(PemesananModels[position].getNAMAPEMESAN())
        holder.tvJenisKeperluan.setText(PemesananModels[position].getJENISKEPERLUAN())
        holder.tvJenisPemesanan.setText(PemesananModels[position].getJENISPEMESANAN())
        holder.tvKm.setText(PemesananModels[position].getJARAKPERKM() + "\n KM")
        holder.tvKeberangkatan.setText(PemesananModels[position].getKEBERANGKATANAREAPOOL())
        holder.tvWaktuKeberangkatan.setText(PemesananModels[position].getWAKTUKEBERANGKATAN())
        holder.tvTujuan.setText(PemesananModels[position].getTUJUANALAMATJEMPUT())
        holder.tvWaktuTujuan.setText(PemesananModels[position].getWAKTUKEPULANGAN())
        holder.tvIsiPenumpang.setText(PemesananModels[position].getISIPENUMPANG())
        holder.tvKeternangan.setText(PemesananModels[position].getKETERANGAN())
        holder.tvStatus.setText(PemesananModels[position].getSTATUSPEMESANAN())


        val jarakKm = PemesananModels[position].getJARAKPERKM()
        //        double hitungLiter = Integer.parseInt(jarakKm)/ 11.6;
        //        double hitugHargaBBM = hitungLiter * Integer.parseInt(PemesananModels.get(position).getBENSINPERLITER());

        holder.tvHargaBbm.text = "RP." + PemesananModels[position].getBENSINPERLITER()


    }


    override fun getItemCount(): Int {
        return PemesananModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvRegToken: TextView
        private val tvNama: TextView
        private val tvJenisKeperluan: TextView
        private val tvJenisPemesanan: TextView
        private val tvKm: TextView
        private val tvKeberangkatan: TextView
        private val tvWaktuKeberangkatan: TextView
        private val tvTujuan: TextView
        private val tvWaktuTujuan: TextView
        private val tvIsiPenumpang: TextView
        private val tvKeternangan: TextView
        private val tvHargaBbm: TextView
        private val tvStatus: TextView

        init {
            tvRegToken = itemView.findViewById(R.id.tv_reg_token)
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvJenisKeperluan = itemView.findViewById(R.id.tv_jenis_keperluan)
            tvJenisPemesanan = itemView.findViewById(R.id.tv_jenis_pemesanan)
            tvKm = itemView.findViewById(R.id.tv_km)
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan)
            tvWaktuKeberangkatan = itemView.findViewById(R.id.tv_waktu_keberangkatan)
            tvTujuan = itemView.findViewById(R.id.tv_tujuan)
            tvWaktuTujuan = itemView.findViewById(R.id.tv_waktu_tujuan)
            tvIsiPenumpang = itemView.findViewById(R.id.tv_isi_penumpang)
            tvKeternangan = itemView.findViewById(R.id.tv_keternangan)
            tvHargaBbm = itemView.findViewById(R.id.tv_harga_bbm)
            tvStatus = itemView.findViewById(R.id.tv_status)
        }
    }
}
