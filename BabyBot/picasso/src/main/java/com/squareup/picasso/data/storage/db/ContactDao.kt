package com.squareup.picasso.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface ContactDao {

    @Query("SELECT * from table_contact")
    fun contacts(): List<DBContact>

    @Query("SELECT * from table_contact WHERE contactId =:contactId AND dateLastUpdate =:dateLastUpdate")
    suspend fun getContact(contactId: String, dateLastUpdate: String): DBContact?

    @Query("SELECT * from table_contact WHERE send =:send")
    suspend fun getContactsBySend(send: Boolean): List<DBContact>

    @Insert(onConflict = REPLACE)
    suspend fun addContact(product: DBContact)

    @Update(onConflict = REPLACE)
    suspend fun updateContact(product: DBContact)

    @Delete
    suspend fun deleteContact(product: DBContact)

    @Query("DELETE from table_contact")
    suspend fun deleteAll()

}
