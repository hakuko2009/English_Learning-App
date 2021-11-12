package com.example.englishlearningapp.services

import com.example.englishlearningapp.api.API
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Service {
    companion object{
        private val URL: String = "127.0.0.1:3000/"

        private fun customInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(customInterceptor())
            .build()

        private val clientLogin: OkHttpClient = OkHttpClient.Builder().build()
        fun createService(): API {
            val gson = GsonBuilder()
                .setLenient().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .client(clientLogin)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(API::class.java)
        }
    }
}