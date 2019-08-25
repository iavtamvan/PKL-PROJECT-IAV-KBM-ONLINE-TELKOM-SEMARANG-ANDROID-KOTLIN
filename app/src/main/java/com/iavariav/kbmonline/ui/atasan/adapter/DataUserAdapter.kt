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
import com.iavariav.kbmonline.model.UserModel

import java.util.ArrayList

class DataUserAdapter(private val context: Context, private val userModels: ArrayList<UserModel>) : RecyclerView.Adapter<DataUserAdapter.ViewHolder>() {
    private var id: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        id = sharedPreferences.getString(Config.SHARED_PREF_ID, "")
        holder.tvNamaUser.setText(userModels[position].getNAMAUSER())
        holder.tvNik.setText(userModels[position].getNIKUSER())
        holder.tvRule.setText(userModels[position].getRULEUSER())

    }


    override fun getItemCount(): Int {
        return userModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaUser: TextView
        private val tvNik: TextView
        private val tvRule: TextView

        init {
            tvNamaUser = itemView.findViewById(R.id.tv_nama_user)
            tvNik = itemView.findViewById(R.id.tv_nik)
            tvRule = itemView.findViewById(R.id.tv_rule)
        }
    }
}
