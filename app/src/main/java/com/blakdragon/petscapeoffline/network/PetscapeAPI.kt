package com.blakdragon.petscapeoffline.network

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.network.requests.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PetscapeAPI {

    @POST("api/users")
    suspend fun login(@Body request: LoginRequest) : User
}
