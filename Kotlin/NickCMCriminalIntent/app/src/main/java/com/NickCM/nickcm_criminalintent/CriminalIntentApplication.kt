package com.NickCM.nickcm_criminalintent

import android.app.Application
import com.NickCM.nickcm_criminalintent.database.CrimeRepository

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.Companion.initialize(this)
    }
}