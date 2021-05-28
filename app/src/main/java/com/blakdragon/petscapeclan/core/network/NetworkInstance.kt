package com.blakdragon.petscapeclan.core.network

import com.blakdragon.petscapeclan.BuildConfig
import com.blakdragon.petscapeclan.core.moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkInstance {

    val API: PetscapeAPI

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            addLogger(okHttpClientBuilder)
        }
        val okHttpClient = okHttpClientBuilder.build()

        API = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build().create(PetscapeAPI::class.java)
    }

    private fun addLogger(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
}