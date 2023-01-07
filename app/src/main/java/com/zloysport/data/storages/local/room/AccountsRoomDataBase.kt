package com.zloysport.data.storages.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zloysport.data.models.Account

@Database(entities = [Account::class], version = 1)
abstract class AccountsRoomDataBase : RoomDatabase() {
    abstract fun getAccountsDao(): AccountsDao
}