package com.blakdragon.petscapeoffline.models

import com.blakdragon.petscapeoffline.network.NetworkInstance
import okhttp3.ResponseBody

class NetworkError(val message: String)

fun ResponseBody.toNetworkError(): NetworkError? {
    return NetworkInstance.moshi.adapter(NetworkError::class.java).fromJson(this.string())
}