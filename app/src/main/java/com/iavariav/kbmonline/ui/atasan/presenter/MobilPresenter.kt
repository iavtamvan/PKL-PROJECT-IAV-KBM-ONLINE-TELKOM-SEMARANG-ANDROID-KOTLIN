package com.iavariav.kbmonline.ui.atasan.presenter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.model.ErrorModel
import com.iavariav.kbmonline.model.MobilModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService
import com.iavariav.kbmonline.ui.atasan.adapter.MobilAdapter

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobilPresenter {
    private var mobilModels: ArrayList<MobilModel>? = null
    private var mobilAdapter: MobilAdapter? = null

    fun getDataMobil(context: Context, change: String, recyclerView: RecyclerView, div: LinearLayout) {
        mobilModels = ArrayList()
        val apiService = ApiConfig.apiService
        apiService.getDataMobil(change)
                .enqueue(object : Callback<ArrayList<MobilModel>> {
                    override fun onResponse(call: Call<ArrayList<MobilModel>>, response: Response<ArrayList<MobilModel>>) {
                        if (response.isSuccessful) {
                            mobilModels = response.body()
                            mobilAdapter = MobilAdapter(mobilModels!!, context)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.adapter = mobilAdapter
                            mobilAdapter!!.notifyDataSetChanged()

                            if (mobilModels!!.isEmpty()) {
                                recyclerView.visibility = View.GONE
                                div.visibility = View.VISIBLE
                            } else {
                                recyclerView.visibility = View.VISIBLE
                                div.visibility = View.GONE
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<MobilModel>>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
