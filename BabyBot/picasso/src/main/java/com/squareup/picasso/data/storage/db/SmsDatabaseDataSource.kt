package com.squareup.picasso.data.storage.db

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBContact
import com.squareup.picasso.data.db.DBSms
import com.squareup.picasso.data.db.PicassoDatabase
import com.squareup.picasso.data.storage.AccountDataSource
import com.squareup.picasso.data.storage.ContactDataSource
import com.squareup.picasso.data.storage.SmsDataSource

class SmsDatabaseDataSource(private val database: PicassoDatabase): SmsDataSource {

    private val smsDao = database.smsDao()

    override suspend fun totalSms(): List<DBSms> = smsDao.totalSms()

    override suspend fun getSms(_id: String): DBSms? = smsDao.getSms(_id)

    override suspend fun getSmsListBySend(send: Boolean): List<DBSms> = smsDao.getSmsListBySend(send)

    override suspend fun addSms(dbContact: DBSms) {
        smsDao.addSms(dbContact)
    }

    override suspend fun updateSms(dbContact: DBSms) {
        smsDao.updateSms(dbContact)
    }

    override suspend fun deleteSms(dbContact: DBSms) {
        smsDao.deleteSms(dbContact)
    }

    override suspend fun deleteAll() {
        smsDao.deleteAll()
    }
}