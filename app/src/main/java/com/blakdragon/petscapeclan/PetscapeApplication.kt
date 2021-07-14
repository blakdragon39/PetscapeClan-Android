package com.blakdragon.petscapeclan

import android.app.Application
import com.blakdragon.petscapeclan.core.DataRepo
import com.blakdragon.petscapeclan.core.PetscapePrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PetscapeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        PetscapePrefs.init(this)

        initData()
    }

    private fun initData() = GlobalScope.launch {
        DataRepo.getAchievements()
        DataRepo.getPets()
        DataRepo.getRanks()
    }
}