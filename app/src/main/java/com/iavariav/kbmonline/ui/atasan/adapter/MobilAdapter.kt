package com.iavariav.kbmonline.ui.atasan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.model.MobilModel

import java.util.ArrayList

class MobilAdapter(private val mobilModels: ArrayList<MobilModel>, private val context: FragmentActivity?) : RecyclerView.Adapter<MobilAdapter.ViewHolder>() {

    private var id: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mobil, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        id = sharedPreferences?.getString(Config.SHARED_PREF_ID, "")
        holder.tvPlatMobilStatus.setText(mobilModels[position].platmobil + " | " + mobilModels[position].statusmobil)
        holder.tvJenisMobil.setText(mobilModels[position].jenismobil)
        holder.tvSopirMobil.setText(mobilModels[position].namasupir)
        holder.tvTypeMobil.setText(mobilModels[position].typemobil)
        holder.tvDeskripsiMobil.setText(mobilModels[position].deskripsimobil)

    }


    override fun getItemCount(): Int {
        return mobilModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvPlatMobilStatus: TextView
        val tvJenisMobil: TextView
        val tvTypeMobil: TextView
        val tvSopirMobil: TextView
        val tvDeskripsiMobil: TextView

        init {
            tvPlatMobilStatus = itemView.findViewById(R.id.tv_plat_mobil_status)
            tvJenisMobil = itemView.findViewById(R.id.tv_jenis_mobil)
            tvTypeMobil = itemView.findViewById(R.id.tv_type_mobil)
            tvSopirMobil = itemView.findViewById(R.id.tv_sopir_mobil)
            tvDeskripsiMobil = itemView.findViewById(R.id.tv_deskripsi_mobil)
        }
    }
}
