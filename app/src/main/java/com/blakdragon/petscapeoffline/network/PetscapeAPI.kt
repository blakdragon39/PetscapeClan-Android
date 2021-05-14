package com.blakdragon.petscapeoffline.network

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.network.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.network.requests.LoginRequest
import com.blakdragon.petscapeoffline.network.requests.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PetscapeAPI {

    @POST("api/login/register")
    suspend fun register(@Body request: RegisterRequest) : User

    @POST("api/login/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest) : User

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest) : User
}
