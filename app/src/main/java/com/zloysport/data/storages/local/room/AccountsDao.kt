package com.zloysport.data.storages.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.zloysport.data.models.Account

@Dao
interface AccountsDao {

    @Insert(onConflict = REPLACE)
    fun addAccount(account: Account)

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): List<Account>
}