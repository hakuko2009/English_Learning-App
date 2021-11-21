package com.example.englishlearningapp.api

import com.example.englishlearningapp.data.models.*
import retrofit2.http.*
import retrofit2.Call

interface API {
    @GET("/admin/information/{username}")
    fun getAdminInfo(
        @Path("username") username: String
    ): Call<String>

    @GET("/hocvien")
    fun getAllStudents(
        @Header("Authorization") token: String
    ): Call<String>

    @GET("/hocvien/{username}")
    fun getStudentDetail(
        @Path("username") username: String
    ): Call<String>

    @GET("/giaovien")
    fun getAllTeachers(
        @Header("Authorization") token: String
    ): Call<String>

    @GET("/giaovien/tatcabaihoc/{gv_username}")
    fun getAllLessonsByTeacher(
        @Path("gv_username") gv_username: String
    ): Call<String>

    @GET("/giaovien/{username}")
    fun getTeacherDetail(
        @Path("username") username: String
    ): Call<String>

    @POST("/hocvien/login")
    @FormUrlEncoded
    fun studentLogin(
        @Field("hv_username") username: String,
        @Field("hv_password") password: String
    ): Call<String>

    @POST("/admin/login")
    @FormUrlEncoded
    fun adminLogin(
        @Field("admin_username") username: String,
        @Field("admin_password") password: String
    ): Call<String>

    @GET("/baihoc")
    fun getAllLessons(
        @Header("Authorization") token: String
    ): Call<String>

    @GET("/baihoc/{maBaiHoc}")
    fun getLessonDetail(
        @Path("maBaiHoc") maBaiHoc: String
    ): Call<String>

    @GET("/danhmucbaihoc")
    fun getAllCategories(
        @Header("Authorization") token: String
    ): Call<String>

    @GET("/danhmucbaihoc/{maDanhMuc}")
    fun getCategoryById(
        @Path("maDanhMuc") categoryId: String
    ): Call<String>

    @GET("/danhmucbaihoc/tatcabaihoc/{maDanhMuc}")
    fun getAllLessonsByCateg(
        @Header("Authorization") token: String,
        @Path("maDanhMuc") maDanhMuc: String
    ): Call<String>

    @FormUrlEncoded
    @PUT("/baihoc/{maBaiHoc}")
    fun updateLesson(
        @Path("maBaiHoc") maBaiHoc: String?,
        @Field("MaBaiHoc") lessonId: String?,
        @Field("TenBaiHoc") lessonName: String?,
        @Field("MaDanhMuc") categoryId: String?,
        @Field("GV_username") teacherId: String?,
        @Field("NoiDungBaiHoc") lessonContent: String?,
        @Field("NgayTao") createdDay: String?,
        @Field("MucDanhGiaTB") score: Float?
    ): Call<String>

    @FormUrlEncoded
    @PUT("/hocvien/{username}")
    fun updateStudent(
        @Path("username") username: String?,
        @Field("hv_password") hv_password: String?,
        @Field("tenhv") tenhv: String?,
        @Field("ngaysinh") ngaysinh: String?,
        @Field("gioitinh") gioitinh: Int?,
        @Field("email") email: String?,
        @Field("sdt") sdt: String?,
        @Field("diachi") diachi: String?,
        @Field("avatar") avatar: Any?
    ): Call<String>

    @FormUrlEncoded
    @PUT("/admin/updateInformation/{username}")
    fun updateAdmin(
        @Path("username") username: String?,
        @Field("admin_password") admin_password: String?,
        @Field("email") email: String?,
        @Field("sdt") sdt: String?,
    ): Call<String>
}