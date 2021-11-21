package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Admin(
    @SerializedName("Admin_username")
    @Expose
    var username: String,

    @SerializedName("Admin_password")
    @Expose
    var password: String,

    @SerializedName("Email")
    @Expose
    var email: String?,

    @SerializedName("SDT")
    @Expose
    var tel: String?,

    @SerializedName("refreshToken")
    @Expose
    var refreshToken: String?,
)
