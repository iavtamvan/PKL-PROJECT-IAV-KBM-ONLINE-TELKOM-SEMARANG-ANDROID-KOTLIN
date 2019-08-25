package com.iavariav.kbmonline.ui.user.fragment


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

import androidx.fragment.app.Fragment

import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.material.snackbar.Snackbar
import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.metode.Haversine
import com.iavariav.kbmonline.model.MobilModel
import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService
import com.iavariav.kbmonline.ui.user.presenter.PemesananUserPresenter
import com.jaredrummler.materialspinner.MaterialSpinner

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import java.util.Objects
import java.util.Random

import im.delight.android.location.SimpleLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.app.Activity.RESULT_OK
import android.content.Context.MODE_PRIVATE
import java.lang.Math.acos
import java.lang.Math.cos
import java.lang.Math.sin


/**
 * A simple [Fragment] subclass.
 */
class PemesananMobilFragment : Fragment() {
    private val jeniskeperluan = arrayOf("-- PILIH --", "Reguler", "Sosial", "Event", "CAM", "Emergency", "Penanganan Gangguan", "Direksi")
    private val jenisPemesanan = arrayOf("-- PILIH --", "MOBIL", "MOBIL & SOPIR", "SOPIR")
    private val kawasan = arrayOf("-- PILIH --", "JABAR-BANTEN", "JABODETABEK", "JATEN-DIY", "JATIM-BALI-NUSA", "KALIMANTAN", "PAMASULA")
    private val witel = arrayOf("-- PILIH --", "Jateng Barut", "Jateng Barsel", "Jateng Utara", "Jateng Timur", "Jateng Tengah", "Jateng Selatan", "DI YOgyakarta", "Jateng Timsel")
    private val areaPool = arrayOf("-- PILIH --", "SMG Johar", "SMG Pahlawan")
    private val areaTujuanKawasan = arrayOf("-- PILIH --", "JABAR-BANTEN", "JABODETABEK", "JATEN-DIY", "JATIM-BALI-NUSA", "KALIMANTAN", "PAMASULA")
    private val areaTujuanKawasanPilhan = arrayOf("-- PILIH --", "KS Sudirman", "MGL Yos Sudarso", "PK Merak", "PK Pemuda", "PWT Merdeka", "SLA Diponegoro", "SMH Johar", "SMG Pahlawan", "SLO Mayor Kusmanto", "Yogyakarta", "Lainnya")
    private val jumlahIsiPenumpang = arrayOf("-- PILIH --", "1", "2", "3", "4", "5")

    private var android_id: String? = null
    private var namaPemesan: String? = null
    private var idUser: String? = null
    private var regID: String? = null

    private var placeNameAdress: String? = null
    private var placeName: String? = null

    private var jeniskeperluanSave: String? = null
    private var jenisPemesananSave: String? = null
    private var jenisPemesananMobilSave: String? = null
    private var kawasanSave: String? = null
    private var witelSave: String? = null
    private var areaPoolSave: String? = null
    private var areaTujuanKawasanSave: String? = null
    private var areaTujuanKawasanPilhanSave: String? = null
    private var jumlahIsiPenumpangSave: String? = null

    private var latitudeBerangkat: Double = 0.toDouble()
    private var longitudeBerangkat: Double = 0.toDouble()
    private var latitudeTujuan: Double = 0.toDouble()
    private var longitudeTUjuan: Double = 0.toDouble()
    private var distance: Double = 0.toDouble()
    private var hitungJarak: Double = 0.toDouble()
    internal var stringJarak: Double = 0.toDouble()
    internal var hitungHargaBBM: Double = 0.toDouble()
    private var location: SimpleLocation? = null

    private var myCalendar: Calendar? = null
    private var dateBerangkat: DatePickerDialog.OnDateSetListener? = null
    private var dateKepulangan: DatePickerDialog.OnDateSetListener? = null

    private var spnJenisKeperluan: MaterialSpinner? = null
    private var spnJenisPemesanan: MaterialSpinner? = null
    private var spnKawasan: MaterialSpinner? = null
    private var spnWitel: MaterialSpinner? = null
    private var spnAreaPool: MaterialSpinner? = null
    private var spnJumlahPenumpang: MaterialSpinner? = null
    private var edtPenjemputan: EditText? = null
    private var spnAreaTujuanKawasan: MaterialSpinner? = null
    private var spnAreaTujuan: MaterialSpinner? = null
    private var spnJenisMobil: MaterialSpinner? = null
    private var divTanggalBerangkat: LinearLayout? = null
    private var tvTanggal: TextView? = null
    private var tvKetukTanggalHide: TextView? = null
    private var divWaktuBerangkat: LinearLayout? = null
    private var tvWaktu: TextView? = null
    private var tvKetukWaktuHide: TextView? = null
    private var edtNoTeleponKantor: EditText? = null
    private var edtNoHp: EditText? = null
    private var edtKeterangan: EditText? = null
    private var tvNamaAtasan: TextView? = null
    private var tvTokenPemesanan: TextView? = null
    private var divTanggalKepulangan: LinearLayout? = null
    private var tvTanggalKepulangan: TextView? = null
    private var tvKetukTanggalHideKepulangan: TextView? = null
    private var divWaktuKepulangan: LinearLayout? = null
    private var tvWaktuKepulangan: TextView? = null
    private var tvKetukWaktuHideKepulangan: TextView? = null
    private var btnLocDetail: Button? = null
    private var tvAlamatDetail: TextView? = null
    private var edtIsiPenumpang: EditText? = null

    private var pemesananUserPresenter: PemesananUserPresenter? = null
    private var btnPesanSekarang: Button? = null
    private var mobilModels: ArrayList<MobilModel>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pemesanan_mobil, container, false)
        initView(view)
        mobilModels = ArrayList()
        pemesananUserPresenter = PemesananUserPresenter()

        val sharedPreferences = activity!!.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        namaPemesan = sharedPreferences.getString(Config.SHARED_PREF_NAMA_LENGKAP, "")
        idUser = sharedPreferences.getString(Config.SHARED_PREF_ID, "")
        regID = sharedPreferences.getString("regId", "")


        location = SimpleLocation(activity!!)
        // if we can't access the location yet
        if (!location!!.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(activity!!)
        }
        latitudeBerangkat = location!!.latitude
        longitudeBerangkat = location!!.longitude
        //        Toast.makeText(getActivity(), "" + latitudeBerangkat + longitudeBerangkat, Toast.LENGTH_SHORT).show();

        android_id = Settings.Secure.getString(context!!.contentResolver,
                Settings.Secure.ANDROID_ID)
        val r = Random()
        val random = r.nextInt(44) * 88
        tvTokenPemesanan!!.text = "TELKOM-KBM$random-$android_id"
        tvNamaAtasan!!.text = "Yani Maria Christie"

        myCalendar = Calendar.getInstance()
        dateBerangkat = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar!!.set(Calendar.YEAR, year)
            myCalendar!!.set(Calendar.MONTH, monthOfYear)
            myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelBerangkat()
        }
        dateKepulangan = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar!!.set(Calendar.YEAR, year)
            myCalendar!!.set(Calendar.MONTH, monthOfYear)
            myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelKepulangan()
        }

        divTanggalBerangkat!!.setOnClickListener { getDate(dateBerangkat) }

        divWaktuBerangkat!!.setOnClickListener { getTime(tvWaktu) }


        divTanggalKepulangan!!.setOnClickListener { getDate(dateKepulangan) }
        divWaktuKepulangan!!.setOnClickListener { getTime(tvWaktuKepulangan) }

        btnLocDetail!!.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            try {
                startActivityForResult(builder.build(Objects.requireNonNull(activity)), PLACE_PICKER_REQUEST)
            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            }
        }


        tvAlamatDetail!!.setOnClickListener {
            val navigationIntentUri = Uri.parse("google.navigation:q=$latitudeTujuan,$longitudeTUjuan")//creating intent with latlng
            val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        spnJenisKeperluan!!.setItems(*jeniskeperluan)
        spnJenisKeperluan!!.setOnItemSelectedListener { view, position, id, item ->
            jeniskeperluanSave = item.toString()
            Snackbar.make(view, "Memilih " + jeniskeperluanSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnJenisPemesanan!!.setItems(*jenisPemesanan)
        spnJenisPemesanan!!.setOnItemSelectedListener { view, position, id, item ->
            jenisPemesananSave = item.toString()
            Snackbar.make(view, "Memilih " + jenisPemesananSave!!, Snackbar.LENGTH_LONG).show()

            val apiService = ApiConfig.apiService
            apiService.getDataMobilByStatus("getDataMobilByType", jenisPemesananSave!!)
                    .enqueue(object : Callback<ArrayList<MobilModel>> {
                        override fun onResponse(call: Call<ArrayList<MobilModel>>, response: Response<ArrayList<MobilModel>>) {
                            if (response.isSuccessful) {
                                mobilModels = response.body()
                                Toast.makeText(activity, "" + mobilModels!!, Toast.LENGTH_SHORT).show()
                                for (i in mobilModels!!.indices) {
                                    spnJenisMobil!!.setItems<String>(mobilModels!![i].typemobil + " " + mobilModels!![i].platmobil)

                                }

                                spnJenisMobil!!.setOnItemSelectedListener { view, position, id, item ->
                                    Toast.makeText(activity, "" + item, Toast.LENGTH_SHORT).show()
                                    jenisPemesananMobilSave = item.toString()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<MobilModel>>, t: Throwable) {
                            Toast.makeText(activity, "" + t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
        }



        spnKawasan!!.setItems(*kawasan)
        spnKawasan!!.setOnItemSelectedListener { view, position, id, item ->
            kawasanSave = item.toString()
            Snackbar.make(view, "Memilih " + kawasanSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnWitel!!.setItems(*witel)
        spnWitel!!.setOnItemSelectedListener { view, position, id, item ->
            witelSave = item.toString()
            Snackbar.make(view, "Memilih " + witelSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnAreaPool!!.setItems(*areaPool)
        spnAreaPool!!.setOnItemSelectedListener { view, position, id, item ->
            areaPoolSave = item.toString()
            Snackbar.make(view, "Memilih " + areaPoolSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnAreaTujuanKawasan!!.setItems(*areaTujuanKawasan)
        spnAreaTujuanKawasan!!.setOnItemSelectedListener { view, position, id, item ->
            areaTujuanKawasanSave = item.toString()
            Snackbar.make(view, "Memilih " + areaTujuanKawasanSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnAreaTujuan!!.setItems(*areaTujuanKawasanPilhan)
        spnAreaTujuan!!.setOnItemSelectedListener { view, position, id, item ->
            areaTujuanKawasanPilhanSave = item.toString()
            Snackbar.make(view, "Memilih " + areaTujuanKawasanPilhanSave!!, Snackbar.LENGTH_LONG).show()
        }
        spnJumlahPenumpang!!.setItems(*jumlahIsiPenumpang)
        spnJumlahPenumpang!!.setOnItemSelectedListener { view, position, id, item ->
            jumlahIsiPenumpangSave = item.toString()
            Snackbar.make(view, "Memilih " + jumlahIsiPenumpangSave!!, Snackbar.LENGTH_LONG).show()
        }

        btnPesanSekarang!!.setOnClickListener {
            //                pemesananUserPresenter.dataUserPemesanan(getActivity(), "1", namaPemesan
            ////                        , jeniskeperluanSave, jenisPemesananSave, "Mobil H5609JK", kawasanSave, witelSave,
            ////                        areaPoolSave, edtPenjemputan.getText().toString().trim(), areaTujuanKawasanSave + ", " + areaTujuanKawasanPilhanSave, tvAlamatDetail.getText().toString().trim(), String.valueOf(latitudeTujuan),
            ////                        String.valueOf(longitudeBerangkat), String.valueOf(latitudeTujuan), String.valueOf(longitudeTUjuan), tvTanggal.getText().toString() + tvWaktu.getText().toString().trim(),
            ////                        tvTanggalKepulangan.getText().toString().trim() + tvWaktuKepulangan.getText().toString().trim(), edtNoTeleponKantor.getText().toString().trim(),
            ////                        edtNoHp.getText().toString().trim(), jumlahIsiPenumpangSave, edtIsiPenumpang.getText().toString().trim(), edtKeterangan.getText().toString().trim(), String.valueOf(stringJarak), String.valueOf(hitungHargaBBM), "YANI",
            ////                        tvTokenPemesanan.getText().toString().trim());
            pemesananUserPresenter!!.dataUserPemesanan(
                    activity,
                    "1",
                    namaPemesan,
                    jeniskeperluanSave,
                    jenisPemesananSave,
                    jenisPemesananMobilSave,
                    kawasanSave,
                    witelSave,
                    areaPoolSave,
                    edtPenjemputan!!.text.toString().trim { it <= ' ' },
                    "$areaTujuanKawasanSave, $areaTujuanKawasanPilhanSave",
                    "$placeName, $placeNameAdress",
                    latitudeBerangkat.toString(),
                    longitudeBerangkat.toString(),
                    latitudeTujuan.toString(),
                    longitudeTUjuan.toString(),
                    tvTanggal!!.text.toString().trim { it <= ' ' } + ", " + tvWaktu!!.text.toString().trim { it <= ' ' },
                    tvTanggalKepulangan!!.text.toString().trim { it <= ' ' } + ", " + tvWaktuKepulangan!!.text.toString().trim { it <= ' ' },
                    edtNoTeleponKantor!!.text.toString().trim { it <= ' ' },
                    edtNoHp!!.text.toString().trim { it <= ' ' },
                    jumlahIsiPenumpangSave,
                    edtIsiPenumpang!!.text.toString().trim { it <= ' ' },
                    edtKeterangan!!.text.toString().trim { it <= ' ' },
                    stringJarak.toString(),
                    hitungHargaBBM.toString(),
                    "Yani Maria Christie",
                    tvTokenPemesanan!!.text.toString().trim { it <= ' ' },
                    regID
            )
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PLACE_PICKER_REQUEST -> {
                    val place = PlacePicker.getPlace(activity!!, data!!)
                    placeNameAdress = String.format("%s", place.address)
                    placeName = String.format("%s", place.name)
                    latitudeTujuan = place.latLng.latitude
                    longitudeTUjuan = place.latLng.longitude

                    //                    tvAlamatDetail.setText(placeName + ", " + placeNameAdress);
                    //                    getDistance(latitudeBerangkat, longitudeBerangkat, latitudeTujuan, longitudeTUjuan);
                    hitungJarak = Haversine.hitungJarak(latitudeBerangkat, longitudeBerangkat, latitudeTujuan, longitudeTUjuan)
                    stringJarak = java.lang.Double.parseDouble(String.format("%.2f", hitungJarak))
                    hitungHargaBBM = stringJarak / 11.5 * 7650
                    //                    tvAlamatDetail.setText(stringJarak + ">>> " + "Rp." + hitungHargaBBM);
                    tvAlamatDetail!!.text = "$placeName, $placeNameAdress"
                }
            }
        }
    }

    private fun getTime(textView: TextView?) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> textView!!.text = "$selectedHour:$selectedMinute" }, hour, minute, true)//Yes 24 hour time
        mTimePicker.setTitle("Pilh Waktu")
        mTimePicker.show()
    }

    private fun getDate(type: DatePickerDialog.OnDateSetListener?) {
        DatePickerDialog(activity!!, type, myCalendar!!
                .get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabelBerangkat() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvTanggal!!.text = sdf.format(myCalendar!!.time)
    }

    private fun updateLabelKepulangan() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvTanggalKepulangan!!.text = sdf.format(myCalendar!!.time)
    }

    fun getDistance(firstLat: Double?, firstLong: Double?, secondLat: Double?, secondLong: Double?): Double? {

        val phi1 = firstLat!! * PI_RAD

        val phi2 = secondLat!! * PI_RAD

        val lam1 = firstLong!! * PI_RAD

        val lam2 = secondLong!! * PI_RAD


        distance = 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1))


        return distance

    }

    private fun initView(view: View) {
        spnJenisKeperluan = view.findViewById(R.id.spn_jenis_keperluan)
        spnJenisPemesanan = view.findViewById(R.id.spn_jenis_pemesanan)
        spnKawasan = view.findViewById(R.id.spn_kawasan)
        spnWitel = view.findViewById(R.id.spn_witel)
        spnAreaPool = view.findViewById(R.id.spn_area_pool)
        edtPenjemputan = view.findViewById(R.id.edt_penjemputan)
        spnAreaTujuanKawasan = view.findViewById(R.id.spn_area_tujuan_kawasan)
        spnAreaTujuan = view.findViewById(R.id.spn_area_tujuan)
        divTanggalBerangkat = view.findViewById(R.id.div_tanggal)
        tvTanggal = view.findViewById(R.id.tv_tanggal)
        tvKetukTanggalHide = view.findViewById(R.id.tv_ketuk_tanggal_hide)
        divWaktuBerangkat = view.findViewById(R.id.div_waktu)
        tvWaktu = view.findViewById(R.id.tv_waktu)
        tvKetukWaktuHide = view.findViewById(R.id.tv_ketuk_waktu_hide)
        edtNoTeleponKantor = view.findViewById(R.id.edt_no_telepon_kantor)
        edtNoHp = view.findViewById(R.id.edt_no_hp)
        edtKeterangan = view.findViewById(R.id.edt_keterangan)
        tvNamaAtasan = view.findViewById(R.id.tv_nama_atasan)
        tvTokenPemesanan = view.findViewById(R.id.tv_token_pemesanan)
        divTanggalKepulangan = view.findViewById(R.id.div_tanggal_kepulangan)
        tvTanggalKepulangan = view.findViewById(R.id.tv_tanggal_kepulangan)
        tvKetukTanggalHideKepulangan = view.findViewById(R.id.tv_ketuk_tanggal_hide_kepulangan)
        divWaktuKepulangan = view.findViewById(R.id.div_waktu_kepulangan)
        tvWaktuKepulangan = view.findViewById(R.id.tv_waktu_kepulangan)
        tvKetukWaktuHideKepulangan = view.findViewById(R.id.tv_ketuk_waktu_hide_kepulangan)
        btnLocDetail = view.findViewById(R.id.btn_loc_detail)
        tvAlamatDetail = view.findViewById(R.id.tv_alamat_detail)
        spnJumlahPenumpang = view.findViewById(R.id.spn_jumlah_penumpang)
        edtIsiPenumpang = view.findViewById(R.id.edt_isi_penumpang)
        btnPesanSekarang = view.findViewById(R.id.btn_pesan_sekarang)
        spnJenisMobil = view.findViewById(R.id.spn_jenis_mobil)
    }

    companion object {

        private val PLACE_PICKER_REQUEST = 999

        internal var PI_RAD = Math.PI / 180.0
    }
}// Required empty public constructor
