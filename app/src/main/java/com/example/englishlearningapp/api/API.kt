package com.example.englishlearningapp.api

import com.example.englishlearningapp.data.models.*
import retrofit2.http.*
import retrofit2.Call

interface API {
    @GET("/hocvien")
    fun getAllStudents(
        @Header("Authorization") token: String
    ): Call<List<Student>>

    @GET("/hocvien/{username}")
    fun getStudentDetail(
        @Path("username") username: String
    ): Call<Student>

    @GET("/giaovien")
    fun getAllTeachers(
        @Header("Authorization") token: String
    ): Call<List<Teacher>>

    @GET("/giaovien/tatcabaihoc/{gv_username}")
    fun getAllLessonsByTeacher(
        @Path("gv_username") gv_username: String
    ): Call<List<Lesson>>

    @GET("/hocvien/{username}")
    fun getTeacherDetail(
        @Path("username") username: String
    ): Call<Teacher>

    @POST("/hocvien/login")
    @FormUrlEncoded
    fun studentLogin(
        @Field("hv_username") username :String,
        @Field("hv_password") password :String
    ): Call<Login>

    @POST("/admin/login")
    @FormUrlEncoded
    fun adminLogin(
        @Field("admin_username") username :String,
        @Field("admin_password") password :String
    ): Call<Login>

    @GET("/baihoc")
    fun getAllLessons(
        @Header("Authorization") token: String
    ): Call<List<Lesson>>

    @GET("/baihoc/{maBaiHoc}")
    fun getLessonDetail(
        @Path("maBaiHoc") maBaiHoc: String
    ): Call<List<Lesson>>

    @GET("/danhmucbaihoc")
    fun getAllCategories(
        @Header("Authorization") token: String
    ): Call<List<Category>>

    @GET("/danhmucbaihoc/tatcabaihoc/{maDanhMuc}")
    fun getAllLessonsByCateg(
        @Header("Authorization") token: String,
        @Path("maDanhMuc") maDanhMuc: String
    ): Call<List<Lesson>>

}