package com.example.englishlearningapp.mvvm.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Admin
import com.example.englishlearningapp.mvvm.models.UpdateAdminAccountModel
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAccountViewModel: ViewModel(){
    private val api = Service.createService()
    val gson = Gson()
    val liveAdmin = MutableLiveData<Admin>()
    var updateModel = UpdateAdminAccountModel("", "", "")
    val liveUpdateResult = MutableLiveData<String>()

    val email: TextWatcher
        get() = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                updateModel.email = s.toString()
            }
        }
    val tel: TextWatcher
        get() = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                updateModel.tel = s.toString()
            }
        }

    fun getAccountInfo(username: String){
        api.getAdminInfo(username).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val raw = JSONObject(responseBody).toString()
                    val ad: Admin = gson.fromJson(raw, Admin::class.java)

                    liveAdmin.value = ad
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }
    fun updateAccountInfo(username: String, password: String) {
        api.updateAdmin(username, password, updateModel.email, updateModel.tel).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val mess = JSONObject(responseBody).getString("message")

                    liveUpdateResult.value = mess
                }
                else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                val mess = t.message.toString()
                Log.d("FAILED TAG", mess)
                liveUpdateResult.value = mess
            }
        })
    }
}