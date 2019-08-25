package com.iavariav.kbmonline.ui.atasan.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.ui.atasan.presenter.HistoriAtasanPresenter

/**
 * A simple [Fragment] subclass.
 */
class HistoriAtasanFragment : Fragment() {
    private var rv: RecyclerView? = null
    private var idUser: String? = null

    private var historiAtasanPresenter: HistoriAtasanPresenter? = null
    private var div: LinearLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_histori_atasan, container, false)
        initView(view)

        val sharedPreferences = activity!!.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString(Config.SHARED_PREF_ID, "")

        historiAtasanPresenter = HistoriAtasanPresenter()
        historiAtasanPresenter!!.getDatas(activity, idUser, rv, div)

        return view
    }


    private fun initView(view: View) {
        rv = view.findViewById(R.id.rv)
        div = view.findViewById(R.id.div)
    }
}// Required empty public constructor
