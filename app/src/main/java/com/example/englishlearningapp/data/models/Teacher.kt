package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Teacher(
    @SerializedName("GV_username")
    @Expose
    var username: String,

    @SerializedName("GV_password")
    @Expose
    var password: String,

    @SerializedName("TenGV")
    @Expose
    var name: String?,

    @SerializedName("IDNumber")
    @Expose
    var idNumb: String?,

    @SerializedName("NgaySinh")
    @Expose
    var dayOfBirth: String?,

    @SerializedName("GioiTinh")
    @Expose
    var gender: Int?,

    @SerializedName("Email")
    @Expose
    var email: String?,

    @SerializedName("SDT")
    @Expose
    var tel: String?,

    @SerializedName("DiaChi")
    @Expose
    var address: String?,

    @SerializedName("DVCT")
    @Expose
    var workingPlace: String?,

    @SerializedName("Avatar")
    @Expose
    var avatar: Any?,

    @SerializedName("TongSoBaiHoc")
    @Expose
    var totalLessons: Int?,

    @SerializedName("refreshToken")
    @Expose
    var refreshToken: String?,

)