package com.iavariav.kbmonline.model

import com.google.gson.annotations.SerializedName

class MobilModel {

    @SerializedName("TYPE_MOBIL")
    var typemobil: String? = null

    @SerializedName("STATUS_MOBIL")
    var statusmobil: String? = null

    @SerializedName("ID_MOBIL")
    var idmobil: String? = null

    @SerializedName("DESKRIPSI_MOBIL")
    var deskripsimobil: String? = null

    @SerializedName("PLAT_MOBIL")
    var platmobil: String? = null

    @SerializedName("NAMA_SUPIR")
    var namasupir: String? = null

    @SerializedName("JENIS_MOBIL")
    var jenismobil: String? = null
}