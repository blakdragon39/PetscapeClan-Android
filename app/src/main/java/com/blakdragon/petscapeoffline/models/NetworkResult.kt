package com.blakdragon.petscapeoffline.models

import java.lang.IllegalStateException

class NetworkResult<D>(
    private val data: D? = null,
    private val exception: Exception? = null
) {
    var handled: Boolean = false
        private set

    val isSuccessful = data != null && exception == null

    fun getUnhandledData(): D {
        return if (!handled) {
            handled = true
            data!!
        } else {
            throw IllegalStateException()
        }
    }

    fun getUnhandledException(): Exception {
        return if (!handled) {
            handled = true
            exception!!
        } else {
            throw IllegalStateException()
        }
    }
}