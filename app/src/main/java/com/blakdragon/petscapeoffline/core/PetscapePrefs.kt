package com.blakdragon.petscapeoffline.core

import android.content.Context
import android.content.SharedPreferences
import com.blakdragon.petscapeoffline.models.User

object PetscapePrefs {

    private const val PREFS_NAME = "petscape.prefs"

    private const val KEY_USER = "key.user"

    private lateinit var prefs: SharedPreferences

    fun init(c: Context) {
        prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var user: User?
        get() {
            return if (prefs.contains(KEY_USER)) {
                moshi.adapter(User::class.java).fromJson(prefs.getString(KEY_USER, "")!!)
            } else {
                null
            }
        }
        set(value) {
            val editor = prefs.edit()

            if (value != null) {
                editor.putString(KEY_USER, moshi.adapter(User::class.java).toJson(value))
            } else {
                editor.remove(KEY_USER)
            }

            editor.apply()
        }
}