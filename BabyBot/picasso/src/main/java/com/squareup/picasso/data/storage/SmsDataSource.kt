package com.squareup.picasso.data.storage

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBContact
import com.squareup.picasso.data.db.DBSms

interface SmsDataSource {
    suspend fun totalSms(): List<DBSms>
    suspend fun getSms(_id: String): DBSms?
    suspend fun getSmsListBySend(send: Boolean): List<DBSms>
    suspend fun addSms(dbContact: DBSms)
    suspend fun updateSms(dbContact: DBSms)
    suspend fun deleteSms(dbContact: DBSms)
    suspend fun deleteAll()
}