package com.iavariav.kbmonline.ui.atasan.presenter

import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.ui.atasan.adapter.AtasanAprovalAdapter

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AprovalPresenter {
    private var PemesananModels: ArrayList<PemesananModel>? = null
    private var atasanAprovalAdapter: AtasanAprovalAdapter? = null

    fun getDatas(context: FragmentActivity?, rv: RecyclerView?, div: LinearLayout?) {
        PemesananModels = ArrayList()
        val apiService = ApiConfig.apiService
        apiService.getAllData("getAllDataPemesanan")
                .enqueue(object : Callback<ArrayList<PemesananModel>> {
                    override fun onResponse(call: Call<ArrayList<PemesananModel>>, response: Response<ArrayList<PemesananModel>>) {
                        if (response.isSuccessful) {
                            PemesananModels = response.body()
                            atasanAprovalAdapter = AtasanAprovalAdapter(context, PemesananModels!!)
                            rv?.layoutManager = LinearLayoutManager(context)
                            rv?.adapter = atasanAprovalAdapter
                            atasanAprovalAdapter!!.notifyDataSetChanged()

                            if (PemesananModels!!.isEmpty()) {
                                rv?.visibility = View.GONE
                                div?.visibility = View.VISIBLE
                            } else {
                                rv?.visibility = View.VISIBLE
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
