package com.zloysport.di

import android.app.Application

class ZloySportApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent.factory().createApplicationComponentFactory(this)
    }
}