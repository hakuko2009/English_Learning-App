package com.example.englishlearningapp.`interface`

interface LoginResultCallBack {
    fun onSuccess(token: String, username: String)
    fun onError(message: String)
}