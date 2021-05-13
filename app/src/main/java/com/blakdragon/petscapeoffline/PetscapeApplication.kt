package com.blakdragon.petscapeoffline

import android.app.Application
import timber.log.Timber

class PetscapeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}