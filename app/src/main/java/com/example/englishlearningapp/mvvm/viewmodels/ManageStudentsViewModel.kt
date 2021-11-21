package com.example.englishlearningapp.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Student
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageStudentsViewModel: ViewModel() {
    private val api = Service.createService()
    val liveStudentList = MutableLiveData<List<Student>>()
    val liveStudent = MutableLiveData<Student>()
    val gson = Gson()

    fun getStudentList(token: String){
        api.getAllStudents(token = "Bearer $token").enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val jsonArray = JSONArray(responseBody)
                    val result = ArrayList<Student>()

                    for(i in 0 until jsonArray.length()){
                        val raw = JSONObject(jsonArray.get(i).toString()).toString()
                        val student: Student = gson.fromJson(raw, Student::class.java)

                        result.add(student)
                    }
                    liveStudentList.value = result
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }

    fun getStudentDetail(username: String){
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
}