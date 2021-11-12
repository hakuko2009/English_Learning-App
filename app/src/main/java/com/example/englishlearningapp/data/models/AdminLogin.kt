package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AdminLogin {
    @SerializedName("msg")
    @Expose
    val msg: String? = null

    @SerializedName("accessToken")
    @Expose
    val accessToken: String? = null

    @SerializedName("user")
    @Expose
    val admin: Admin? = null

}