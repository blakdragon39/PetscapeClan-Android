package com.blakdragon.petscapeclan.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.models.toNetworkError
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    fun handleError(e: Exception) {
        Timber.e(e)

        var message = when (e) {
            is HttpException -> e.response()?.errorBody()?.toNetworkError()?.message
            else -> null
        }

        if (message.isNullOrEmpty()) message = getString(R.string.network_error)

        showMessage(message)
    }

    fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}