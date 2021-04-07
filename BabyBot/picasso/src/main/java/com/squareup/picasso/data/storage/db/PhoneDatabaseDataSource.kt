package com.squareup.picasso.data.storage.db

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBPhone
import com.squareup.picasso.data.db.PicassoDatabase
import com.squareup.picasso.data.storage.AccountDataSource
import com.squareup.picasso.data.storage.PhoneDataSource

class PhoneDatabaseDataSource(private val database: PicassoDatabase): PhoneDataSource {

    private val phoneDao = database.phoneDao()

    override suspend fun phones(): List<DBPhone> = phoneDao.phones()

    override suspend fun getPhone(imei: String): DBPhone? = phoneDao.getPhone(imei)

    override suspend fun getPhonesBySend(send: Boolean): List<DBPhone> = phoneDao.getPhonesBySend(send)

    override suspend fun addPhone(dbPhone: DBPhone) {
        phoneDao.addPhone(dbPhone)
    }

    override suspend fun updatePhone(dbPhone: DBPhone) {
        phoneDao.updatePhone(dbPhone)
    }

    override suspend fun deletePhone(dbPhone: DBPhone) {
        phoneDao.deletePhone(dbPhone)
    }

    override suspend fun deleteAll() {
        phoneDao.deleteAll()
    }
}