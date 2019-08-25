package com.iavariav.kbmonline.ui.user.presenter

import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.ui.user.adapter.HistoriAdapter

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoriUserPresenter {
    private var pemesananModels: ArrayList<PemesananModel>? = null
    private var historiAdapter: HistoriAdapter? = null

    fun getDataHistoriUser(context: FragmentActivity?, namaPemesan: String?, recyclerView: RecyclerView?, div: LinearLayout?) {
        pemesananModels = ArrayList()
        val apiService = ApiConfig.apiService
        apiService.getDataUserHistoriPemesanan("getDataPemesanan", namaPemesan)
                .enqueue(object : Callback<ArrayList<PemesananModel>> {
                    override fun onResponse(call: Call<ArrayList<PemesananModel>>, response: Response<ArrayList<PemesananModel>>) {
                        if (response.isSuccessful) {
                            pemesananModels = response.body()
                            historiAdapter = HistoriAdapter(context, pemesananModels!!)
                            recyclerView?.layoutManager = LinearLayoutManager(context)
                            recyclerView?.adapter = historiAdapter
                            historiAdapter!!.notifyDataSetChanged()

                            if (pemesananModels!!.isEmpty()) {
                                recyclerView?.visibility = View.GONE
                                div?.visibility = View.VISIBLE
                            } else {
                                recyclerView?.visibility = View.VISIBLE
                                div?.visibility = View.GONE
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<PemesananModel>>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
