package com.blakdragon.petscapeoffline.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blakdragon.petscapeoffline.R

abstract class BaseActivity : AppCompatActivity() {

    fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}