package com.zloysport.di.modules

import com.zloysport.data.interactors.LoginInteractor
import com.zloysport.di.StateHoldersProvider
import com.zloysport.ui.states.LoginStateHolder
import dagger.Module
import dagger.Provides
import com.zloysport.ui.states.AllDrillsStateHolder

@Module
class StateHoldersModule {

    @Provides
    fun provideLoginStateHolder(
        loginInteractor: LoginInteractor
    ): LoginStateHolder {
        return StateHoldersProvider.getOrCreateStateHolder(
            StateHoldersProvider.LOGIN,
        ) {
            LoginStateHolder(loginInteractor)
        }
    }

    @Provides
    fun provideAllDrillsState(): AllDrillsStateHolder {
        return AllDrillsStateHolder()
    }
}