package com.example.englishlearningapp.mvvm.models

data class UpdateStudentAccountModel(
    var password: String?,
    var name: String?,
    var birthDay: String?,
    var email: String?,
    var tel: String?,
    var address: String?,
    var avatar: Any? ) {
}