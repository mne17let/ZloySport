package com.zloysport.di.modules

import android.content.Context
import androidx.room.Room
import com.zloysport.data.storages.local.room.AccountsDao
import com.zloysport.data.storages.local.room.AccountsRoomDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideAccountsRoomDataBase(
        applicationContext: Context
    ): AccountsRoomDataBase {
        return Room.databaseBuilder(
            applicationContext,
            AccountsRoomDataBase::class.java,
            "zloy-sport-accounts"
        ).build()
    }

    @Provides
    fun provideAccountsDao(
        accountsRoomDataBase: AccountsRoomDataBase
    ): AccountsDao {
        return accountsRoomDataBase.getAccountsDao()
    }
}