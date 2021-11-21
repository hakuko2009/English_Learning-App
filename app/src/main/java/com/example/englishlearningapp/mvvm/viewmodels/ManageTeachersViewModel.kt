package com.example.englishlearningapp.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.data.models.Teacher
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageTeachersViewModel: ViewModel() {
    private val api = Service.createService()
    val liveTeacherList = MutableLiveData<List<Teacher>>()
    val liveLessonListbyTeacher = MutableLiveData<List<Lesson>>()
    val liveTeacher = MutableLiveData<Teacher>()
    val gson = Gson()

    fun getTeacherList(token: String){
        api.getAllTeachers(token = "Bearer $token").enqueue(object: Callback<String> {
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
                    Log.d("Success teacher tag", result.size.toString())
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
        api.getTeacherDetail(username).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val rawTeacher = JSONObject(responseBody).toString()
                    val teacher: Teacher = gson.fromJson(rawTeacher, Teacher::class.java)

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

    fun getAllLessonbyTeacher(username: String){
        api.getAllLessonsByTeacher(username).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val jsonArray = JSONArray(responseBody)
                    val result = ArrayList<Lesson>()

                    for(i in 0 until jsonArray.length()){
                        val raw = JSONObject(jsonArray.get(i).toString()).toString()
                        val lesson: Lesson = gson.fromJson(raw, Lesson::class.java)

                        result.add(lesson)
                    }
                    liveLessonListbyTeacher.value = result
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