package com.zloysport.di.modules

import com.zloysport.data.storages.local.LocalStorage
import com.zloysport.data.storages.local.room.AccountsDao
import com.zloysport.data.storages.local.room.LocalStorageImpl
import dagger.Module
import dagger.Provides

@Module
class StoragesModule {

    @Provides
    fun provideLocalStorage(
        accountsDao: AccountsDao
    ): LocalStorage {
        return LocalStorageImpl(accountsDao)
    }
}