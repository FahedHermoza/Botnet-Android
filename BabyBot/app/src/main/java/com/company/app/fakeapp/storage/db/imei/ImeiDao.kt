package com.company.app.fakeapp.storage.db.imei

import androidx.room.*

@Dao
interface ImeiDao {

    @Query("SELECT * from imei_table")
    suspend fun getAllImei(): List<Imei>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Imei): Long

    @Update
    suspend fun update(usuario: Imei): Int

}