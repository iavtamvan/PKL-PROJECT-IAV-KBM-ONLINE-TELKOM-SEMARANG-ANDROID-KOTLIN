package com.iavariav.kbmonline.model

import com.google.gson.annotations.SerializedName

class ErrorModel {

    @SerializedName("error_msg")
    var errorMsg: String? = null

    @SerializedName("error")
    var isError: Boolean = false
}