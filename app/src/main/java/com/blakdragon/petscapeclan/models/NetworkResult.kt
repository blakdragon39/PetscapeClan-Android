package com.blakdragon.petscapeclan.models

import java.lang.IllegalStateException

class NetworkResult<D>(
    private val data: D? = null,
    private val exception: Exception? = null
) {
    var exceptionHandled: Boolean = false
        private set

    val isSuccessful = data != null && exception == null

    fun getData(): D {
        return data ?: throw IllegalStateException("Data doesn't exist")
    }

    fun getUnhandledException(): Exception {
        return if (!exceptionHandled) {
            exceptionHandled = true
            exception!!
        } else {
            throw IllegalStateException("Exception already handled")
        }
    }
}