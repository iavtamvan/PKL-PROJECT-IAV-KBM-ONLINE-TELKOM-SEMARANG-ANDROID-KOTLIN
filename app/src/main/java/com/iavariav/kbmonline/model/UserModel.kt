package com.iavariav.kbmonline.model

import com.google.gson.annotations.SerializedName

class UserModel {

    @SerializedName("NAMA_USER")
    var namauser: String? = null

    @SerializedName("PASSWORD_USER")
    var passworduser: String? = null

    @SerializedName("RULE_USER")
    var ruleuser: String? = null

    @SerializedName("NIK_USER")
    var nikuser: String? = null

    @SerializedName("ID_USER")
    var iduser: String? = null

    @SerializedName("REG_ID")
    var regid: String? = null
}