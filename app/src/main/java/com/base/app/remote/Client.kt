package com.base.app.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Client {

    fun getInstance():Api{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val original: Request = it.request()

            val request: Request = original.newBuilder()
                .header("XApiKey", "")
                .method(original.method, original.body)
                .build()

            it.proceed(request)
        }
        return Retrofit.Builder()
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://github.com/")
            .build()
            .create(Api::class.java)
    }

}
