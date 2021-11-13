package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("HV_username")
    @Expose
    var username: String,

    @SerializedName("HV_password")
    @Expose
    var password: String,

    @SerializedName("TenHV")
    @Expose
    var name: String,

    @SerializedName("NgaySinh")
    @Expose
    var dayOfBirth: String,

    @SerializedName("GioiTinh")
    @Expose
    var gender: Boolean,

    @SerializedName("Email")
    @Expose
    var email: String,

    @SerializedName("SDT")
    @Expose
    var tel: String,

    @SerializedName("DiaChi")
    @Expose
    var address: String,

    @SerializedName("Avatar")
    @Expose
    var avatar: String,

    @SerializedName("SLBaiDaHoc")
    @Expose
    var learntLessons: Int,

    @SerializedName("NgayDK")
    @Expose
    var dayOfReg: String,

    @SerializedName("refreshToken")
    @Expose
    var refreshToken: String,

)


