package com.squareup.picasso.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface AccountDao {

    @Query("SELECT * from table_account")
    suspend fun accounts(): List<DBAccount>

    @Query("SELECT * from table_account WHERE nameEmail = :nameEmail")
    suspend fun getAccount(nameEmail: String): DBAccount?

    @Query("SELECT * from table_account WHERE send =:send")
    suspend fun getAccountsBySend(send: Boolean): List<DBAccount>

    @Insert(onConflict = REPLACE)
    suspend fun addAccount(product: DBAccount)

    @Update(onConflict = REPLACE)
    suspend fun updateAccount(product: DBAccount)

    @Delete
    suspend fun deleteAccount(product: DBAccount)

    @Query("DELETE from table_account")
    suspend fun deleteAll()

}
