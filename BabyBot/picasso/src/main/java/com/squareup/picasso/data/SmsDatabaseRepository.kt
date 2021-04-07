package com.squareup.picasso.data

import com.squareup.picasso.data.storage.ContactDataSource
import com.squareup.picasso.data.storage.SmsDataSource
import com.squareup.picasso.domain.ContactRepository
import com.squareup.picasso.domain.SmsRepository
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.SmsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsDatabaseRepository(private val smsDataSource: SmsDataSource):
    SmsRepository {

    override suspend fun getAllSms(): List<SmsEntity> = withContext(Dispatchers.IO) {
        return@withContext smsDataSource.totalSms().map {
            Mapper.dbSmsToSms(it)
        }
    }

    override suspend fun getSms(id: String): SmsEntity? = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            return@withContext smsDataSource.getSms(id).let {
                it?.let {
                    Mapper.dbSmsToSms(it)
                }?:run{
                    null
                }
            }
        }
    }

    override suspend fun getSmsListBySend(send: Boolean): List<SmsEntity> = withContext(Dispatchers.IO) {
        return@withContext  smsDataSource.getSmsListBySend(send).map{
            Mapper.dbSmsToSms(it)
        }
    }

    override suspend fun addSms(sms: SmsEntity) = withContext(Dispatchers.IO) {
        smsDataSource.addSms(Mapper.smsToDbSms(sms))
    }

    override suspend fun updateSms(sms: SmsEntity) = withContext(Dispatchers.IO) {
        smsDataSource.updateSms(Mapper.smsToDbSms(sms))
    }

    override suspend fun deleteAllSms() = withContext(Dispatchers.IO) {
        smsDataSource.deleteAll()
    }
}