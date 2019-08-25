package com.iavariav.kbmonline.ui.atasan.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.ui.atasan.presenter.AprovalPresenter

import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class AprovalFragment : Fragment() {
    private var rv: RecyclerView? = null

    private var aprovalPresenter: AprovalPresenter? = null
    private var div: LinearLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_aproval, container, false)
        initView(view)
        aprovalPresenter = AprovalPresenter()
        aprovalPresenter!!.getDatas(activity, rv, div)


        return view
    }

    private fun initView(view: View) {
        rv = view.findViewById(R.id.rv)
        div = view.findViewById(R.id.div)
    }
}// Required empty public constructor
