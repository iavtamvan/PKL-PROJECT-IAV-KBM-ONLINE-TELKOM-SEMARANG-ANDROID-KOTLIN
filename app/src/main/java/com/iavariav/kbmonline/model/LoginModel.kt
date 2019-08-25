package com.iavariav.kbmonline.model

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("error_msg")
    var errorMsg: String? = null

    @SerializedName("rule")
    var rule: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("error")
    var isError: Boolean = false

    @SerializedName("username")
    var username: String? = null
}