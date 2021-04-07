package com.squareup.picasso.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface SmsDao {

    @Query("SELECT * from table_sms")
    fun totalSms(): List<DBSms>

    @Query("SELECT * from table_sms WHERE id =:id")
    suspend fun getSms(id: String): DBSms?

    @Query("SELECT * from table_sms WHERE send =:send")
    suspend fun getSmsListBySend(send: Boolean): List<DBSms>

    @Insert(onConflict = REPLACE)
    suspend fun addSms(product: DBSms)

    @Update(onConflict = REPLACE)
    suspend fun updateSms(product: DBSms)

    @Delete
    suspend fun deleteSms(product: DBSms)

    @Query("DELETE from table_sms")
    suspend fun deleteAll()

}
