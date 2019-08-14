package com.iavariav.kbmonline.ui.user.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.snackbar.Snackbar;
import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.metode.Haversine;
import com.iavariav.kbmonline.ui.user.presenter.PemesananUserPresenter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import im.delight.android.location.SimpleLocation;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A simple {@link Fragment} subclass.
 */
public class PemesananMobilFragment extends Fragment {
    private String jeniskeperluan[] = {"-- PILIH --", "Reguler", "Sosial", "Event", "CAM", "Emergency", "Penanganan Gangguan", "Direksi"};
    private String jenisPemesanan[] = {"-- PILIH --", "MOBIL", "MOBIL + SOPIR", "SOPIR"};
    private String kawasan[] = {"-- PILIH --", "JABAR-BANTEN", "JABODETABEK", "JATEN-DIY", "JATIM-BALI-NUSA", "KALIMANTAN", "PAMASULA"};
    private String witel[] = {"-- PILIH --", "Jateng Barut", "Jateng Barsel", "Jateng Utara", "Jateng Timur", "Jateng Tengah", "Jateng Selatan", "DI YOgyakarta", "Jateng Timsel"};
    private String areaPool[] = {"-- PILIH --", "SMG Johar", "SMG Pahlawan"};
    private String areaTujuanKawasan[] = {"-- PILIH --", "JABAR-BANTEN", "JABODETABEK", "JATEN-DIY", "JATIM-BALI-NUSA", "KALIMANTAN", "PAMASULA"};
    private String areaTujuanKawasanPilhan[] = {"-- PILIH --", "KS Sudirman", "MGL Yos Sudarso", "PK Merak", "PK Pemuda", "PWT Merdeka", "SLA Diponegoro", "SMH Johar", "SMG Pahlawan", "SLO Mayor Kusmanto", "Yogyakarta", "Lainnya"};
    private String jumlahIsiPenumpang[] = {"-- PILIH --", "1", "2", "3", "4", "5"};

    private String android_id;
    private String namaPemesan;
    private String idUser;
    private String regID;

    private String placeNameAdress;
    private String placeName;

    private String jeniskeperluanSave;
    private String jenisPemesananSave;
    private String kawasanSave;
    private String witelSave;
    private String areaPoolSave;
    private String areaTujuanKawasanSave;
    private String areaTujuanKawasanPilhanSave;
    private String jumlahIsiPenumpangSave;

    private final static int PLACE_PICKER_REQUEST = 999;

    private double latitudeBerangkat;
    private double longitudeBerangkat;
    private double latitudeTujuan;
    private double longitudeTUjuan;
    private double distance;
    private double hitungJarak;
    double stringJarak;
    double hitungHargaBBM;
    private SimpleLocation location;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateBerangkat;
    private DatePickerDialog.OnDateSetListener dateKepulangan;

    private MaterialSpinner spnJenisKeperluan;
    private MaterialSpinner spnJenisPemesanan;
    private MaterialSpinner spnKawasan;
    private MaterialSpinner spnWitel;
    private MaterialSpinner spnAreaPool;
    private MaterialSpinner spnJumlahPenumpang;
    private EditText edtPenjemputan;
    private MaterialSpinner spnAreaTujuanKawasan;
    private MaterialSpinner spnAreaTujuan;
    private LinearLayout divTanggalBerangkat;
    private TextView tvTanggal;
    private TextView tvKetukTanggalHide;
    private LinearLayout divWaktuBerangkat;
    private TextView tvWaktu;
    private TextView tvKetukWaktuHide;
    private EditText edtNoTeleponKantor;
    private EditText edtNoHp;
    private EditText edtKeterangan;
    private TextView tvNamaAtasan;
    private TextView tvTokenPemesanan;
    private LinearLayout divTanggalKepulangan;
    private TextView tvTanggalKepulangan;
    private TextView tvKetukTanggalHideKepulangan;
    private LinearLayout divWaktuKepulangan;
    private TextView tvWaktuKepulangan;
    private TextView tvKetukWaktuHideKepulangan;
    private Button btnLocDetail;
    private TextView tvAlamatDetail;
    private EditText edtIsiPenumpang;

    static double PI_RAD = Math.PI / 180.0;

    private PemesananUserPresenter pemesananUserPresenter;
    private Button btnPesanSekarang;


    public PemesananMobilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pemesanan_mobil, container, false);
        initView(view);
        pemesananUserPresenter = new PemesananUserPresenter();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        namaPemesan = sharedPreferences.getString(Config.SHARED_PREF_NAMA_LENGKAP, "");
        idUser = sharedPreferences.getString(Config.SHARED_PREF_ID, "");
        regID = sharedPreferences.getString("regId", "");


        location = new SimpleLocation(getActivity());
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(getActivity());
        }
        latitudeBerangkat = location.getLatitude();
        longitudeBerangkat = location.getLongitude();
//        Toast.makeText(getActivity(), "" + latitudeBerangkat + longitudeBerangkat, Toast.LENGTH_SHORT).show();

        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Random r = new Random();
        int random = (r.nextInt(44) * 88);
        tvTokenPemesanan.setText("TELKOM-KBM" + random + "-" + android_id);

        myCalendar = Calendar.getInstance();
        dateBerangkat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelBerangkat();
            }
        };
        dateKepulangan = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelKepulangan();
            }
        };

        divTanggalBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(dateBerangkat);

            }
        });

        divWaktuBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime(tvWaktu);
            }
        });


        divTanggalKepulangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate(dateKepulangan);
            }
        });
        divWaktuKepulangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime(tvWaktuKepulangan);
            }
        });

        btnLocDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Objects.requireNonNull(getActivity())), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        tvAlamatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + latitudeTujuan + "," + longitudeTUjuan);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        spnJenisKeperluan.setItems(jeniskeperluan);
        spnJenisKeperluan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                jeniskeperluanSave = item;
                Snackbar.make(view, "Memilih " + jeniskeperluanSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnJenisPemesanan.setItems(jenisPemesanan);
        spnJenisPemesanan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                jenisPemesananSave = item;
                Snackbar.make(view, "Memilih " + jenisPemesananSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnKawasan.setItems(kawasan);
        spnKawasan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                kawasanSave = item;
                Snackbar.make(view, "Memilih " + kawasanSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnWitel.setItems(witel);
        spnWitel.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                witelSave = item;
                Snackbar.make(view, "Memilih " + witelSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnAreaPool.setItems(areaPool);
        spnAreaPool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                areaPoolSave = item;
                Snackbar.make(view, "Memilih " + areaPoolSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnAreaTujuanKawasan.setItems(areaTujuanKawasan);
        spnAreaTujuanKawasan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                areaTujuanKawasanSave = item;
                Snackbar.make(view, "Memilih " + areaTujuanKawasanSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnAreaTujuan.setItems(areaTujuanKawasanPilhan);
        spnAreaTujuan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                areaTujuanKawasanPilhanSave = item;
                Snackbar.make(view, "Memilih " + areaTujuanKawasanPilhanSave, Snackbar.LENGTH_LONG).show();
            }
        });
        spnJumlahPenumpang.setItems(jumlahIsiPenumpang);
        spnJumlahPenumpang.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                jumlahIsiPenumpangSave = item;
                Snackbar.make(view, "Memilih " + jumlahIsiPenumpangSave, Snackbar.LENGTH_LONG).show();
            }
        });

        btnPesanSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pemesananUserPresenter.dataUserPemesanan(getActivity(), "1", namaPemesan
////                        , jeniskeperluanSave, jenisPemesananSave, "Mobil H5609JK", kawasanSave, witelSave,
////                        areaPoolSave, edtPenjemputan.getText().toString().trim(), areaTujuanKawasanSave + ", " + areaTujuanKawasanPilhanSave, tvAlamatDetail.getText().toString().trim(), String.valueOf(latitudeTujuan),
////                        String.valueOf(longitudeBerangkat), String.valueOf(latitudeTujuan), String.valueOf(longitudeTUjuan), tvTanggal.getText().toString() + tvWaktu.getText().toString().trim(),
////                        tvTanggalKepulangan.getText().toString().trim() + tvWaktuKepulangan.getText().toString().trim(), edtNoTeleponKantor.getText().toString().trim(),
////                        edtNoHp.getText().toString().trim(), jumlahIsiPenumpangSave, edtIsiPenumpang.getText().toString().trim(), edtKeterangan.getText().toString().trim(), String.valueOf(stringJarak), String.valueOf(hitungHargaBBM), "YANI",
////                        tvTokenPemesanan.getText().toString().trim());
                pemesananUserPresenter.dataUserPemesanan(
                        getActivity(),
                "1",
                namaPemesan,
                jeniskeperluanSave,
                jenisPemesananSave,
                "MOBIL H55609", // ini masih static
                kawasanSave,
                witelSave,
                areaPoolSave,
                edtPenjemputan.getText().toString().trim(),
                areaTujuanKawasanSave + ", " + areaTujuanKawasanPilhanSave,
                placeName + ", " + placeNameAdress,
                String.valueOf(latitudeBerangkat),
                String.valueOf(longitudeBerangkat),
                String.valueOf(latitudeTujuan),
                String.valueOf(longitudeTUjuan),
                tvTanggal.getText().toString().trim() + ", " + tvWaktu.getText().toString().trim(),
                tvTanggalKepulangan.getText().toString().trim() + ", " + tvWaktuKepulangan.getText().toString().trim(),
                edtNoTeleponKantor.getText().toString().trim(),
                edtNoHp.getText().toString().trim(),
                jumlahIsiPenumpangSave,
                edtIsiPenumpang.getText().toString().trim(),
                edtKeterangan.getText().toString().trim(),
                String.valueOf(stringJarak),
                String.valueOf(hitungHargaBBM),
                "Yani Maria Christie",
                tvTokenPemesanan.getText().toString().trim(),
                        regID
                );
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(getActivity(), data);
                     placeNameAdress = String.format("%s", place.getAddress());
                     placeName= String.format("%s", place.getName());
                    latitudeTujuan = place.getLatLng().latitude;
                    longitudeTUjuan = place.getLatLng().longitude;

//                    tvAlamatDetail.setText(placeName + ", " + placeNameAdress);
//                    getDistance(latitudeBerangkat, longitudeBerangkat, latitudeTujuan, longitudeTUjuan);
                    hitungJarak = Haversine.hitungJarak(latitudeBerangkat, longitudeBerangkat, latitudeTujuan, longitudeTUjuan);
                    stringJarak = Double.parseDouble(String.format("%.2f", hitungJarak));
                    hitungHargaBBM = (stringJarak / 11.5) * 7650;
//                    tvAlamatDetail.setText(stringJarak + ">>> " + "Rp." + hitungHargaBBM);
                    tvAlamatDetail.setText(placeName + ", " + placeNameAdress);
            }
        }
    }

    private void getTime(final TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Pilh Waktu");
        mTimePicker.show();
    }

    private void getDate(DatePickerDialog.OnDateSetListener type) {
        new DatePickerDialog(getActivity(), type, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabelBerangkat() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelKepulangan() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggalKepulangan.setText(sdf.format(myCalendar.getTime()));
    }

    public Double getDistance(Double firstLat, Double firstLong, Double secondLat, Double secondLong) {

        double phi1 = firstLat * PI_RAD;

        double phi2 = secondLat * PI_RAD;

        double lam1 = firstLong * PI_RAD;

        double lam2 = secondLong * PI_RAD;


        distance = 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));


        return distance;

    }

    private void initView(View view) {
        spnJenisKeperluan = view.findViewById(R.id.spn_jenis_keperluan);
        spnJenisPemesanan = view.findViewById(R.id.spn_jenis_pemesanan);
        spnKawasan = view.findViewById(R.id.spn_kawasan);
        spnWitel = view.findViewById(R.id.spn_witel);
        spnAreaPool = view.findViewById(R.id.spn_area_pool);
        edtPenjemputan = view.findViewById(R.id.edt_penjemputan);
        spnAreaTujuanKawasan = view.findViewById(R.id.spn_area_tujuan_kawasan);
        spnAreaTujuan = view.findViewById(R.id.spn_area_tujuan);
        divTanggalBerangkat = view.findViewById(R.id.div_tanggal);
        tvTanggal = view.findViewById(R.id.tv_tanggal);
        tvKetukTanggalHide = view.findViewById(R.id.tv_ketuk_tanggal_hide);
        divWaktuBerangkat = view.findViewById(R.id.div_waktu);
        tvWaktu = view.findViewById(R.id.tv_waktu);
        tvKetukWaktuHide = view.findViewById(R.id.tv_ketuk_waktu_hide);
        edtNoTeleponKantor = view.findViewById(R.id.edt_no_telepon_kantor);
        edtNoHp = view.findViewById(R.id.edt_no_hp);
        edtKeterangan = view.findViewById(R.id.edt_keterangan);
        tvNamaAtasan = view.findViewById(R.id.tv_nama_atasan);
        tvTokenPemesanan = view.findViewById(R.id.tv_token_pemesanan);
        divTanggalKepulangan = view.findViewById(R.id.div_tanggal_kepulangan);
        tvTanggalKepulangan = view.findViewById(R.id.tv_tanggal_kepulangan);
        tvKetukTanggalHideKepulangan = view.findViewById(R.id.tv_ketuk_tanggal_hide_kepulangan);
        divWaktuKepulangan = view.findViewById(R.id.div_waktu_kepulangan);
        tvWaktuKepulangan = view.findViewById(R.id.tv_waktu_kepulangan);
        tvKetukWaktuHideKepulangan = view.findViewById(R.id.tv_ketuk_waktu_hide_kepulangan);
        btnLocDetail = view.findViewById(R.id.btn_loc_detail);
        tvAlamatDetail = view.findViewById(R.id.tv_alamat_detail);
        spnJumlahPenumpang = view.findViewById(R.id.spn_jumlah_penumpang);
        edtIsiPenumpang = view.findViewById(R.id.edt_isi_penumpang);
        btnPesanSekarang = view.findViewById(R.id.btn_pesan_sekarang);
    }
}
