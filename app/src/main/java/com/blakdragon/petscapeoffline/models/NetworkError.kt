package com.blakdragon.petscapeoffline.models

import com.blakdragon.petscapeoffline.core.moshi
import com.blakdragon.petscapeoffline.core.network.NetworkInstance
import okhttp3.ResponseBody

class NetworkError(val message: String)

fun ResponseBody.toNetworkError(): NetworkError? {
    return moshi.adapter(NetworkError::class.java).fromJson(this.string())
}