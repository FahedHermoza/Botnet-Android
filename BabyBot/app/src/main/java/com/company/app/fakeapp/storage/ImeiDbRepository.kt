package com.company.app.fakeapp.storage

import com.company.app.fakeapp.model.ImeiEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImeiDbRepository(private val dataSource: ImeiDataSource) {

    suspend fun getAllNotes(): List<ImeiEntity> = withContext(Dispatchers.IO) {
        val result = dataSource.getAllImei().map { Mapper.dbImeiToImeiEntity(it) }
        return@withContext result
    }

    suspend fun insertImei(imeiEntity: ImeiEntity) = withContext(Dispatchers.IO) {
        dataSource.insert(Mapper.imeiEntityToDbImei(imeiEntity))
    }

    suspend fun updateImei(imeiEntity: ImeiEntity) = withContext(Dispatchers.IO) {
        dataSource.update(Mapper.imeiEntityToDbImei(imeiEntity))
    }
}