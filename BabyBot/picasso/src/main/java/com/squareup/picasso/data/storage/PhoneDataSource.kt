package com.squareup.picasso.data.storage

import com.squareup.picasso.data.db.DBPhone

interface PhoneDataSource {
    suspend fun phones(): List<DBPhone>
    suspend fun getPhone(imei: String): DBPhone?
    suspend fun getPhonesBySend(send: Boolean): List<DBPhone>
    suspend fun addPhone(dbPhone: DBPhone)
    suspend fun updatePhone(dbPhone: DBPhone)
    suspend fun deletePhone(dbPhone: DBPhone)
    suspend fun deleteAll()
}