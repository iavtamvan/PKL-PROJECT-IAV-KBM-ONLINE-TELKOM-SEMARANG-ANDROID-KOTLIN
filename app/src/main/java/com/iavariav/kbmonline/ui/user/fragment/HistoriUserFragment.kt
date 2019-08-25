package com.iavariav.kbmonline.ui.user.fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.ui.user.presenter.HistoriUserPresenter

/**
 * A simple [Fragment] subclass.
 */
class HistoriUserFragment : Fragment() {


    private var rv: RecyclerView? = null
    private var historiUserPresenter: HistoriUserPresenter? = null
    private var div: LinearLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_histori_user, container, false)
        initView(view)
        val sharedPreferences = activity!!.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val namaLengkap = sharedPreferences.getString(Config.SHARED_PREF_NAMA_LENGKAP, "")
        //        Toast.makeText(getActivity(), "" + namaLengkap, Toast.LENGTH_SHORT).show();
        historiUserPresenter = HistoriUserPresenter()
        historiUserPresenter!!.getDataHistoriUser(activity, namaLengkap, rv, div)

        return view
    }

    private fun initView(view: View) {
        rv = view.findViewById(R.id.rv)
        div = view.findViewById(R.id.div)
    }
}// Required empty public constructor
