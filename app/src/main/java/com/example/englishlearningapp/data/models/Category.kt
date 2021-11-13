package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("MaDanhMuc")
    @Expose
    val categoryId: String,

    @SerializedName("TenDanhMuc")
    @Expose
    val categoryName: String,

    @SerializedName("SoBaiHoc")
    @Expose
    val totalLesson: Int,
)