package com.fahedhermoza.developer.examplenote01.Models

import com.company.app.fakeapp.storage.db.BitrhdayDatabase
import com.company.app.fakeapp.storage.ImeiDataSource
import com.company.app.fakeapp.storage.db.imei.Imei

open class ImeiDatabaseDataSource(birthdayDatabase: BitrhdayDatabase) :
    ImeiDataSource {

    private val imeiDao = birthdayDatabase.imeiDao()

    override suspend fun getAllImei(): List<Imei> {
        return imeiDao.getAllImei()
    }

    override suspend fun insert(imei: Imei) {
        imeiDao.insert(imei)
    }

    override suspend fun update(imei: Imei) {
        imeiDao.update(imei)
    }


}