package com.finnvocab.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class FinnVocabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("theme_pref", Context.MODE_PRIVATE)
        val theme = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}
