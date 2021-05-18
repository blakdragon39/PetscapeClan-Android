package com.blakdragon.petscapeoffline.core.network

import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.core.network.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.core.network.requests.LoginRequest
import com.blakdragon.petscapeoffline.core.network.requests.RegisterRequest
import com.blakdragon.petscapeoffline.models.ClanMember
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PetscapeAPI {

    @POST("api/login/register")
    suspend fun register(@Body request: RegisterRequest) : User

    @POST("api/login/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest) : User

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest) : User

    @GET("api/clanMembers")
    suspend fun getClanMembers() : List<ClanMember>
}
