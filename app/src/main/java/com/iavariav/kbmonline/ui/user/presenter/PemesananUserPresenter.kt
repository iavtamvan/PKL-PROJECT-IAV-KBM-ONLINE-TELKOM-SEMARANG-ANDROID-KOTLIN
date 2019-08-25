package com.iavariav.kbmonline.ui.user.presenter

import android.content.Context
import android.widget.Toast

import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PemesananUserPresenter {

    fun dataUserPemesanan(
            context: Context,
            ID_USER_ATASAN: String,
            NAMA_PEMESAN: String,
            JENIS_KEPERLUAN: String,
            JENIS_PEMESANAN: String,
            JENIS_KENDARAAN: String,
            KEBERANGKATAN_KAWASAN: String,
            KEBERANGKATAN_WITEL: String,
            KEBERANGKATAN_AREA_POOL: String,
            TUJUAN_ALAMAT_JEMPUT: String,
            TUJUAN_AREA: String,
            TUJUAN_ALAMAT_DETAIL_MAPS: String,
            LAT_AWAL: String,
            LONG_AWAL: String,
            LAT_TUJUAN: String,
            LONG_TUJUAN: String,
            WAKTU_KEBERANGKATAN: String,
            WAKTU_KEPULANGAN: String,
            NO_TELEPON_KANTOR: String,
            NO_HP: String,
            JUMLAH_PENUMPANG: String,
            ISI_PENUMPANG: String,
            KETERANGAN: String,
            JARAK_PER_KM: String,
            BENSIN_PER_LITER: String,
            NAMA_ATASAN: String,
            REG_TOKEN_PEMESANAN: String,
            REG_ID: String
    ) {

        val apiService = ApiConfig.apiService
        apiService.postDataPemesanan(
                ID_USER_ATASAN,
                NAMA_PEMESAN,
                JENIS_KEPERLUAN,
                JENIS_PEMESANAN,
                JENIS_KENDARAAN,
                KEBERANGKATAN_KAWASAN,
                KEBERANGKATAN_WITEL,
                KEBERANGKATAN_AREA_POOL,
                TUJUAN_ALAMAT_JEMPUT,
                TUJUAN_AREA,
                TUJUAN_ALAMAT_DETAIL_MAPS,
                LAT_AWAL,
                LONG_AWAL,
                LAT_TUJUAN,
                LONG_TUJUAN,
                WAKTU_KEBERANGKATAN,
                WAKTU_KEPULANGAN,
                NO_TELEPON_KANTOR,
                NO_HP,
                JUMLAH_PENUMPANG,
                ISI_PENUMPANG,
                KETERANGAN,
                JARAK_PER_KM,
                BENSIN_PER_LITER,
                NAMA_ATASAN,
                REG_TOKEN_PEMESANAN,
                REG_ID
        )
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body()!!.string())
                                val error = jsonObject.optString("error_msg")
                                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show()
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
}
