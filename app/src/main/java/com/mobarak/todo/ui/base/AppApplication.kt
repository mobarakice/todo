package com.mobarak.todo.ui.base

import android.app.Application
import android.content.Context

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: AppApplication? = null
            private set
        val appContext: Context
            get() = instance!!.applicationContext
    }
}