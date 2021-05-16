package com.blakdragon.petscapeoffline.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blakdragon.petscapeoffline.R
import com.blakdragon.petscapeoffline.models.toNetworkError
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    fun handleError(e: Exception) {
        Timber.e(e)

        val defaultMessage = getString(R.string.network_error)

        val message = when (e) {
            is HttpException -> e.response()?.errorBody()?.toNetworkError()?.message
            else -> defaultMessage
        }
        showMessage(message ?: defaultMessage)
    }

    fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}