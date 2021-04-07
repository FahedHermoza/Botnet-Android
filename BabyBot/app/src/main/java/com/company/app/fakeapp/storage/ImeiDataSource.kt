package com.company.app.fakeapp.storage

import com.company.app.fakeapp.storage.db.imei.Imei

interface ImeiDataSource {

    suspend fun getAllImei(): List<Imei>

    suspend fun insert(imei: Imei)

    suspend fun update(imei: Imei)

}