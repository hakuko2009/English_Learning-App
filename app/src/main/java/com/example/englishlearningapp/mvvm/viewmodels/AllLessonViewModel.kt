package com.example.englishlearningapp.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishlearningapp.data.models.Lesson
import com.example.englishlearningapp.services.Service
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllLessonViewModel: ViewModel() {
    val api = Service.createService()
    val liveLessonList = MutableLiveData<List<Lesson>>()
    val gson = Gson()

    fun getAllLessonList(token: String){
        api.getAllLessons(token = "Bearer $token").enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!.toString()
                    val jsonObject = JSONObject(responseBody)
                    val array: JSONArray = jsonObject.getJSONArray("")
                    val result = ArrayList<Lesson>()

                    for(i in 0 until array.length()){
                        val item = JSONObject(array.get(i).toString())

                        val rawLesson = item.getJSONObject("").toString()
                        val lesson: Lesson = gson.fromJson(rawLesson, Lesson::class.java)

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
}