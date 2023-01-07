package com.zloysport.di.modules

import com.zloysport.data.storages.local.LocalStorage
import com.zloysport.ui.GlobalState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [StateHoldersModule::class, InteractorsModule::class, StoragesModule::class, DataBaseModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideGlobalState(
        localStorage: LocalStorage
    ): GlobalState {
        return GlobalState(localStorage)
    }
}