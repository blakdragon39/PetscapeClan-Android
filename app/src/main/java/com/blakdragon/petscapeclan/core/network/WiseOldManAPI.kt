package com.blakdragon.petscapeclan.core.network

import com.blakdragon.petscapeclan.models.WiseOldManPlayer
import retrofit2.http.GET
import retrofit2.http.Path

interface WiseOldManAPI {

    @GET("players/username/{runescapeName}")
    suspend fun getPlayer(@Path("runescapeName") runescapeName: String): WiseOldManPlayer
}