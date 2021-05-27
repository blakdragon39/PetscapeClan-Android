package com.blakdragon.petscapeclan.core.network

import com.blakdragon.petscapeclan.core.PetscapePrefs
import com.blakdragon.petscapeclan.models.User
import com.blakdragon.petscapeclan.core.network.requests.GoogleLoginRequest
import com.blakdragon.petscapeclan.core.network.requests.LoginRequest
import com.blakdragon.petscapeclan.core.network.requests.RegisterRequest
import com.blakdragon.petscapeclan.models.ClanMemberRequest
import com.blakdragon.petscapeclan.models.ClanMember
import retrofit2.http.*

interface PetscapeAPI {

    @POST("api/login/register")
    suspend fun register(@Body request: RegisterRequest): User

    @POST("api/login/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest): User

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): User

    @GET("api/clanMembers")
    suspend fun getClanMembers(
        @Header("Authorization") token: String? = PetscapePrefs.user?.token
    ): List<ClanMember>

    @POST("api/clanMembers")
    suspend fun addClanMember(
        @Body request: ClanMemberRequest,
        @Header("Authorization") token: String? = PetscapePrefs.user?.token
    ): ClanMember

    @PUT("api/clanMembers")
    suspend fun updateClanMember(
        @Body request: ClanMemberRequest,
        @Header("Authorization") token: String? = PetscapePrefs.user?.token
    ): ClanMember

    @PUT("api/clanMembers/{id}")
    suspend fun pingClanMember(
        @Path("id") memberId: String,
        @Header("Authorization") token: String? = PetscapePrefs.user?.token
    ): ClanMember

    @PUT("api/clanMembers/{id}/lastSeen")
    suspend fun updateLastSeen(
        @Path("id") memberId: String,
        @Header("Authorization") token: String? = PetscapePrefs.user?.token
    ): ClanMember
}
