package com.innocv.otpdispatcher.data

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiFactory {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hooks.slack.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient)
        .build()


    fun <T> create(api: Class<T>): T {
        return retrofit.create(api)
    }

}
