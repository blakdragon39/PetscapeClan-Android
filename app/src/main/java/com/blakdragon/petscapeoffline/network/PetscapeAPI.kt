package com.blakdragon.petscapeoffline.network

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.network.requests.GoogleLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PetscapeAPI {

    @POST("api/login/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest) : User
}
