package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.core.moshi
import okhttp3.ResponseBody

class NetworkError(val message: String)

fun ResponseBody.toNetworkError(): NetworkError? {
    return moshi.adapter(NetworkError::class.java).fromJson(this.string())
}