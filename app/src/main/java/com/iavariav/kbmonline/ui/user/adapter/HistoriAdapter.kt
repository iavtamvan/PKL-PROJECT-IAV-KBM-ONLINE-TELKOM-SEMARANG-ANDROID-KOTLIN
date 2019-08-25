package com.iavariav.kbmonline.ui.user.adapter

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

class HistoriAdapter(private val context: Context, private val PemesananModels: ArrayList<PemesananModel>) : RecyclerView.Adapter<HistoriAdapter.ViewHolder>() {
    private var id: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_histori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        id = sharedPreferences.getString(Config.SHARED_PREF_ID, "")
        holder.tvRegToken.setText(PemesananModels[position].regtokenpemesanan)
        holder.tvNama.setText(PemesananModels[position].namapemesan)
        holder.tvJenisKeperluan.setText(PemesananModels[position].jeniskeperluan)
        holder.tvJenisPemesanan.setText(PemesananModels[position].jenispemesanan)
        holder.tvKm.setText(PemesananModels[position].jarakperkm + "\n KM")
        holder.tvKeberangkatan.setText(PemesananModels[position].keberangkatanareapool)
        holder.tvWaktuKeberangkatan.setText(PemesananModels[position].waktukeberangkatan)
        holder.tvTujuan.setText(PemesananModels[position].tujuanalamatjemput)
        holder.tvWaktuTujuan.setText(PemesananModels[position].waktukepulangan)
        holder.tvIsiPenumpang.setText(PemesananModels[position].isipenumpang)
        holder.tvKeternangan.setText(PemesananModels[position].keterangan)
        holder.tvStatus.setText(PemesananModels[position].statuspemesanan)


//        val jarakKm = PemesananModels[position].getJARAKPERKM()
        //        double hitungLiter = Integer.parseInt(jarakKm)/ 11.6;
        //        double hitugHargaBBM = hitungLiter * Integer.parseInt(PemesananModels.get(position).getBENSINPERLITER());

        holder.tvHargaBbm.text = "RP." + PemesananModels[position].bensinperliter


    }


    override fun getItemCount(): Int {
        return PemesananModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvRegToken: TextView
        val tvNama: TextView
        val tvJenisKeperluan: TextView
        val tvJenisPemesanan: TextView
        val tvKm: TextView
        val tvKeberangkatan: TextView
        val tvWaktuKeberangkatan: TextView
        val tvTujuan: TextView
        val tvWaktuTujuan: TextView
        val tvIsiPenumpang: TextView
        val tvKeternangan: TextView
        val tvHargaBbm: TextView
        val tvStatus: TextView

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
