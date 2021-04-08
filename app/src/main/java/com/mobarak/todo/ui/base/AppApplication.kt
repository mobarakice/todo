package com.mobarak.todo.ui.base

import android.app.Application
import android.content.Context

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: AppApplication

        val appContext: Context
            get() = instance.applicationContext
    }
}