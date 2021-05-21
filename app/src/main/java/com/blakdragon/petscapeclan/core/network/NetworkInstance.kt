package com.blakdragon.petscapeclan.core.network

import com.blakdragon.petscapeclan.BuildConfig
import com.blakdragon.petscapeclan.core.moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkInstance {

    private const val BASE_URL = "http://10.0.2.2:8080/" //todo flavours

    private const val WISE_OLD_MAN_URL = "https://api.wiseoldman.net/"

    val API: PetscapeAPI

    val wiseOldManAPI: WiseOldManAPI

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            addLogger(okHttpClientBuilder)
        }
        val okHttpClient = okHttpClientBuilder.build()

        API = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build().create(PetscapeAPI::class.java)

        wiseOldManAPI = Retrofit.Builder()
            .baseUrl(WISE_OLD_MAN_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build().create(WiseOldManAPI::class.java)
    }

    private fun addLogger(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
}