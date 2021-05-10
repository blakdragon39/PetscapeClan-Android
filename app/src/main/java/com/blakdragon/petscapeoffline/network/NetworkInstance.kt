package com.blakdragon.petscapeoffline.network

import retrofit2.Retrofit

object NetworkInstance {

    private const val BASE_URL = "localhost:8080/" //todo flavours

    val API: PetscapeAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build().create(PetscapeAPI::class.java)
}