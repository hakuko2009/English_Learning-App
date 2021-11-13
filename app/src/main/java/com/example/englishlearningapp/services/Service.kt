package com.example.englishlearningapp.services

import com.example.englishlearningapp.api.API
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class Service {
    companion object{
        private const val URL: String = "http://192.168.1.7:3000"

        private fun customInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
        val lists = listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);
        private val client = OkHttpClient.Builder()
            .connectionSpecs(lists)
            .addInterceptor(customInterceptor())
            .build()

        fun createService(): API {
            val gson = GsonBuilder()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(API::class.java)
        }
    }
}