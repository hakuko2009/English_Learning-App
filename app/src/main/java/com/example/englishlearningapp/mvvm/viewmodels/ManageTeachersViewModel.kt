package com.example.englishlearningapp.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.englishlearningapp.data.models.Teacher
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageTeachersViewModel {
    private val api = Service.createService()
    val liveTeacherList = MutableLiveData<List<Teacher>>()
    val liveTeacher = MutableLiveData<Teacher>()
    val gson = Gson()

    fun getTeacherList(token: String){
        api.getAllLessons(token = "Bearer $token").enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val jsonArray = JSONArray(responseBody)
                    val result = ArrayList<Teacher>()

                    for(i in 0 until jsonArray.length()){
                        val raw = JSONObject(jsonArray.get(i).toString()).toString()
                        val teacher: Teacher = gson.fromJson(raw, Teacher::class.java)

                        result.add(teacher)
                    }
                    liveTeacherList.value = result
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }

    fun getTeacherDetail(username: String){
        api.getLessonDetail(username).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val rawLesson = JSONObject(responseBody).toString()
                    val teacher: Teacher = gson.fromJson(rawLesson, Teacher::class.java)

                    liveTeacher.value = teacher
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