package com.example.englishlearningapp.mvvm.viewmodels

import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.englishlearningapp.`interface`.LoginResultCallBack
import com.example.englishlearningapp.mvvm.models.LoginModel

class LoginViewModel(private val callBack: LoginResultCallBack): ViewModel() {
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
        var loginCode: Int = login.validInfor()
        if(loginCode == 1) {
            callBack.onError("Tên đăng nhập không đúng")
        } else if (loginCode == 2) callBack.onError("Mật khẩu không đúng")
        else if (loginCode == 3) callBack.onError("Tên đăng nhâp và mật khẩu không đúng")

        if (loginCode == 4) {
//            val loginApi: API = Service.createService()
//            loginApi.login(login.username,login.password).enqueue(object : Callback<Login>{
//                override fun onResponse(call: Call<Login>, response: Response<Login>) {
//                    Log.d("AAA response code",response.code().toString())
//                    if(response.code()==200){
//                        listerner.onSuccess(response.body()?.accessToken.toString())
//                    }else if(response.code()==400){
//                        listerner.onError("This User does not exist, check your details")
//                    }
//                }
//                override fun onFailure(call: Call<Login>, t: Throwable) {
//                    listerner.onError(t.message!!)
//                }
//
//            })
//        }
        }
    }
    fun onForgetPasswordCLicked(v: View) {
        callBack.onForgetOpen()

    }
}