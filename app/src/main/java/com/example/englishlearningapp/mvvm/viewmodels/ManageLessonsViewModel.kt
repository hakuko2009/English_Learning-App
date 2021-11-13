package com.example.englishlearningapp.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Category
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.data.models.Teacher
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageLessonsViewModel: ViewModel() {
    private val api = Service.createService()
    val liveLessonList = MutableLiveData<List<Lesson>>()
    val liveLesson = MutableLiveData<Lesson>()
    val liveCategory = MutableLiveData<Category>()
    val liveTeacher = MutableLiveData<Teacher>()
    val gson = Gson()

    fun getLessonList(token: String){
        api.getAllLessons(token = "Bearer $token").enqueue(object: Callback<String> {
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
                    liveLessonList.value = result
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }

        })
    }

    fun getLessonDetail(lessonId: String){
        api.getLessonDetail(lessonId).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val rawLesson = JSONObject(responseBody).toString()
                    val lesson: Lesson = gson.fromJson(rawLesson, Lesson::class.java)

                    liveLesson.value = lesson
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }
    fun getCategoryName(categoryId: String){
        api.getCategoryById(categoryId).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val rawCategory = JSONObject(responseBody).toString()
                    val category: Category = gson.fromJson(rawCategory, Category::class.java)

                    liveCategory.value = category
                }else {
                    Log.d("FAILED TAG", response.code().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("FAILED TAG", t.message.toString())
            }
        })
    }
    fun getTeacherName(gv_username: String){
        api.getTeacherDetail(gv_username).enqueue(object: Callback<String>{
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
}