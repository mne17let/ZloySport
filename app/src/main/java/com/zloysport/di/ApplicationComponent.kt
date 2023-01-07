package com.zloysport.di

import android.content.Context
import com.zloysport.MainActivity
import com.zloysport.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun injectMainActivity(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun createApplicationComponentFactory(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}