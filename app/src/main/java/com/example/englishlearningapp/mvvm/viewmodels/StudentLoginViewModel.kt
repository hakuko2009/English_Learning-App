package com.example.englishlearningapp.mvvm.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.`interface`.LoginResultCallBack
import com.example.englishlearningapp.api.API
import com.example.englishlearningapp.data.models.StudentLogin
import com.example.englishlearningapp.mvvm.models.LoginModel
import com.example.englishlearningapp.services.Service
import retrofit2.*

class StudentLoginViewModel(private val callBack: LoginResultCallBack): ViewModel() {
    val login: LoginModel = LoginModel("","")

    val emailTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                login.username = s.toString()
            }
        }

    val passTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                login.password = s.toString()
            }

        }

    fun onLoginClicked(v: View) {
        var loginCode = login.validInfor()
        if(loginCode == 1) {
            callBack.onError("Mật khẩu không đúng")
        } else if (loginCode == 2) callBack.onError("Tên đăng nhập không đúng")
        else if (loginCode == 3) callBack.onError("Tên đăng nhâp và mật khẩu không đúng")

        if (loginCode == 4) {
            val loginApi: API = Service.createService()
            loginApi.studentLogin(login.username, login.password).enqueue(object : Callback<StudentLogin> {
                override fun onResponse(call: Call<StudentLogin>, response: Response<StudentLogin>) {
                    Log.d("Login Successfully: ",response.code().toString())
                    if(response.code() == 200){
                        callBack.onSuccess(response.body()?.accessToken.toString())
                    }else if(response.code() == 401){
                        callBack.onError("Tên đăng nhập không tồn tại hoặc mật khẩu không chính xác")
                    }
                }
                override fun onFailure(call: Call<StudentLogin>, t: Throwable) {
                    callBack.onError(t.message!!)
                }

            })
        }
    }
}