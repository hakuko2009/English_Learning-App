package com.example.englishlearningapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Lesson{
    @SerializedName("MaBaiHoc")
    @Expose
    lateinit var lessonId: String

    @SerializedName("TenBaiHoc")
    @Expose
    lateinit var lessonName: String

    @SerializedName("MaDanhMuc")
    @Expose
    lateinit var categoryId: String

    @SerializedName("GV_username")
    @Expose
    lateinit var teacherUsername: String

    @SerializedName("NoiDungBaiHoc")
    @Expose
    lateinit var content: String

    @SerializedName("NgayTao")
    @Expose
    lateinit var createdDay: String

    @SerializedName("MucDanhGiaTB")
    @Expose
    var score: Float = 0.0F

    fun scoreToString(): String{
        return this.score.toString()
    }
}


