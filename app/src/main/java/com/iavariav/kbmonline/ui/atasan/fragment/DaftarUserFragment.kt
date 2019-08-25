package com.iavariav.kbmonline.ui.atasan.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.fragment.app.Fragment

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.ui.atasan.presenter.DaftarUserPresenter

/**
 * A simple [Fragment] subclass.
 */
class DaftarUserFragment : Fragment() {

    private var daftarUserPresenter: DaftarUserPresenter? = null
    private var edtNamaUser: EditText? = null
    private var edtNik: EditText? = null
    private var edtPassword: EditText? = null
    private var btnDaftarkan: Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_user, container, false)
        initView(view)
        daftarUserPresenter = DaftarUserPresenter()
        btnDaftarkan!!.setOnClickListener {
            if (edtNamaUser!!.text.toString().isEmpty() || edtNik!!.text.toString().isEmpty() || edtPassword!!.text.toString().isEmpty()) {
                Toast.makeText(activity, "Lengkapi data terlebih dahulu.", Toast.LENGTH_SHORT).show()
            } else {
                daftarUserPresenter!!.daftarkanUser(activity, edtNamaUser!!.text.toString().trim { it <= ' ' }, edtNik!!.text.toString().trim { it <= ' ' },
                        edtPassword!!.text.toString().trim { it <= ' ' }, "NULL")
                edtNamaUser!!.setText("")
                edtNik!!.setText("")
                edtPassword!!.setText("")
            }
        }
        return view
    }

    private fun initView(view: View) {
        edtNamaUser = view.findViewById(R.id.edt_nama_user)
        edtNik = view.findViewById(R.id.edt_nik)
        edtPassword = view.findViewById(R.id.edt_password)
        btnDaftarkan = view.findViewById(R.id.btn_daftarkan)
    }
}// Required empty public constructor
