package com.blakdragon.petscapeclan

import android.app.Application
import com.blakdragon.petscapeclan.core.PetscapePrefs
import timber.log.Timber

class PetscapeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        PetscapePrefs.init(this)
    }
}