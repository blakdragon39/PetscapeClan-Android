package com.blakdragon.petscapeoffline

import android.app.Application
import com.google.firebase.FirebaseApp

class PetscapeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}