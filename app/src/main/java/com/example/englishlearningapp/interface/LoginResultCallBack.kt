package com.example.englishlearningapp.`interface`

interface LoginResultCallBack {
    fun onSuccess(token:String)
    fun onError(message: String)
    fun onForgetOpen()
}