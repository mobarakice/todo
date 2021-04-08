package com.mobarak.todo.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val gson = GsonBuilder().setLenient().create()
    private val builder = Retrofit.Builder()
            .baseUrl("BASE_API_URL")
            .addConverterFactory(GsonConverterFactory.create(gson))
    var retrofit = builder.build()
        private set
    private val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    fun <T> createApiClient(serviceClass: Class<T>?): T {
        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}