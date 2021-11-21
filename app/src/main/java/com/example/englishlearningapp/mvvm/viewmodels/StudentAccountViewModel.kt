package com.example.englishlearningapp.mvvm.viewmodels

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Student
import com.example.englishlearningapp.mvvm.models.UpdateStudentAccountModel
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class StudentAccountViewModel: ViewModel() {
    private val api = Service.createService()
    val gson = Gson()
    val liveStudent = MutableLiveData<Student>()
    var updateModel = UpdateStudentAccountModel("", "", "",
                                                "" , "", "", null)
    val liveUpdateResult = MutableLiveData<String>()

    val name: TextWatcher
        get() = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                updateModel.name = s.toString()
            }
        }
    val birthday: TextWatcher
        get() = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            @SuppressLint("SimpleDateFormat")
            override fun afterTextChanged(s: Editable?) {
                val gotDay = SimpleDateFormat("dd/MM/yyyy").parse(s.toString())!!
                updateModel.birthDay = SimpleDateFormat("yyyy-MM-dd").format(gotDay)
            }
        }
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

    val address: TextWatcher
        get() = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                updateModel.address = s.toString()
            }
        }

    fun getAccountInfo(username: String){
        api.getStudentDetail(username).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val rawStudent = JSONObject(responseBody).toString()
                    val student: Student = gson.fromJson(rawStudent, Student::class.java)

                    liveStudent.value = student
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }

    fun updateAccountInfo(username: String, password: String, gender: Int?) {
        api.updateStudent(username, password,
                                    updateModel.name,
                                    updateModel.birthDay,
                                    gender,
                                    updateModel.email,
                                    updateModel.tel,
                                    updateModel.address,
                                    updateModel.avatar
        ).enqueue(object: Callback<String>{
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