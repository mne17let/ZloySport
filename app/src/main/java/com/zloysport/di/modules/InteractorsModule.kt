package com.zloysport.di.modules

import com.zloysport.data.interactors.LoginInteractor
import com.zloysport.data.storages.local.LocalStorage
import dagger.Module
import dagger.Provides

@Module
class InteractorsModule {

    @Provides
    fun provideLoginInteractor(
        localStorage: LocalStorage
    ): LoginInteractor {
        return LoginInteractor(localStorage)
    }
}