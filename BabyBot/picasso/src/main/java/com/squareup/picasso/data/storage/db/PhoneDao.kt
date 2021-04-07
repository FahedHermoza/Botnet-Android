package com.squareup.picasso.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface PhoneDao {

    @Query("SELECT * from table_phone")
    suspend fun phones(): List<DBPhone>

    @Query("SELECT * from table_phone WHERE imei = :imei")
    suspend fun getPhone(imei: String): DBPhone?

    @Query("SELECT * from table_phone WHERE send =:send")
    suspend fun getPhonesBySend(send: Boolean): List<DBPhone>

    @Insert(onConflict = REPLACE)
    suspend fun addPhone(phone: DBPhone)

    @Update(onConflict = REPLACE)
    suspend fun updatePhone(phone: DBPhone)

    @Delete
    suspend fun deletePhone(phone: DBPhone)

    @Query("DELETE from table_phone")
    suspend fun deleteAll()

}
