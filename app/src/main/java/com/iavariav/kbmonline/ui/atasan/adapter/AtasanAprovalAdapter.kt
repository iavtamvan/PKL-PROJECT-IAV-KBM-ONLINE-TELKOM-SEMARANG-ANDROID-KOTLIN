package com.iavariav.kbmonline.ui.atasan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.ui.atasan.AtasanActivity

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtasanAprovalAdapter(private val context: FragmentActivity?, private val PemesananModels: ArrayList<PemesananModel>) : RecyclerView.Adapter<AtasanAprovalAdapter.ViewHolder>() {
    private var id: String? = null
    private var regId: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_atasan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        id = sharedPreferences?.getString(Config.SHARED_PREF_ID, "")
        holder.tvRegToken.text = PemesananModels[position].regtokenpemesanan
        holder.tvNama.text = PemesananModels[position].namapemesan
        holder.tvJenisKeperluan.text = PemesananModels[position].jeniskeperluan
        holder.tvJenisPemesanan.text = PemesananModels[position].jenispemesanan
        holder.tvKm.text = PemesananModels[position].jarakperkm + "\n KM"
        holder.tvKeberangkatan.text = PemesananModels[position].keberangkatanareapool
        holder.tvWaktuKeberangkatan.text = PemesananModels[position].waktukeberangkatan
        holder.tvTujuan.text = PemesananModels[position].tujuanalamatjemput
        holder.tvWaktuTujuan.text = PemesananModels[position].waktukepulangan
        holder.tvIsiPenumpang.text = PemesananModels[position].isipenumpang
        holder.tvKeternangan.text = PemesananModels[position].keterangan
        holder.tvStatus.text = PemesananModels[position].statuspemesanan

        regId = PemesananModels[position].regid
        val jarakKm = PemesananModels[position].jarakperkm
        //        double hitungLiter = Integer.parseInt(jarakKm)/ 11.6;
        //        double hitugHargaBBM = hitungLiter * Integer.parseInt(PemesananModels.get(position).getBENSINPERLITER());

        holder.tvHargaBbm.text = "RP." + PemesananModels[position].bensinperliter

        // jika disetujui
        holder.ivDisetujui.setOnClickListener {
            //                Toast.makeText(context, "Disetujui" + PemesananModels.get(position).getIDPEMESANAN() + "id : " + id, Toast.LENGTH_SHORT).show();
            PemesananModels[position].idpemesanan?.let { it1 -> updateDatas(it1, id, "APPROVED", "Pesanan anda disetujui Pimpinan") }
        }

        // jika ditolak
        holder.ivDitolak.setOnClickListener { PemesananModels[position].idpemesanan?.let { it1 -> updateDatas(it1, id, "NOT APROVVED", "Pesanan anda ditolak oleh Pimpinan") } }


    }

    private fun updateDatas(id: String, idAtasan: String?, status: String, message: String) {
        val apiService = ApiConfig.apiService
        apiService.updateStatusPemesanan(id, idAtasan, status)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body()!!.string())
                                Config.pushNotif(context, "Status Pemesanan", message, "individual", regId)
                                (context as AtasanActivity).setData()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
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
        val ivDisetujui: ImageView
        val ivDitolak: ImageView

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
            ivDisetujui = itemView.findViewById(R.id.iv_disetujui)
            ivDitolak = itemView.findViewById(R.id.iv_ditolak)
        }
    }
}
