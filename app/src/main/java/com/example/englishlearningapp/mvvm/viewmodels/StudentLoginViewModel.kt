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
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.*

class StudentLoginViewModel(private val callBack: LoginResultCallBack): ViewModel() {
    val login: LoginModel = LoginModel("","")
    val gson = Gson()
    val loginApi: API = Service.createService()

    val usernameTextWatcher: TextWatcher
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
        loginApi.studentLogin(login.username, login.password).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("Login Successfully: ",response.code().toString())
                if(response.code() == 200){
                    val resBody = response.body()!!.toString()
                    val jsonObject = JSONObject(resBody).toString()
                    val result: StudentLogin = gson.fromJson(jsonObject, StudentLogin::class.java)

                    callBack.onSuccess(result.accessToken.toString())
                }else if(response.code() == 401){
                    callBack.onError("Tên đăng nhập không tồn tại hoặc mật khẩu không chính xác")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(t.message!!)
                Log.d("Login Failed: ", t.message!!)
            }
        })

    }
}