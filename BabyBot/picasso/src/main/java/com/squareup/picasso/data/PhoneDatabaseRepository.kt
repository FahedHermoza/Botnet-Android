package com.squareup.picasso.data

import com.squareup.picasso.data.storage.AccountDataSource
import com.squareup.picasso.data.storage.PhoneDataSource
import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.PhoneRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.PhoneEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhoneDatabaseRepository(private val phoneDataSource: PhoneDataSource): PhoneRepository {

    override suspend fun getAllPhones(): List<PhoneEntity>  = withContext(Dispatchers.IO) {
        return@withContext phoneDataSource.phones().map {
            Mapper.dbPhoneToPhone(it)
        }
    }

    override suspend fun getPhone(imei: String): PhoneEntity? =
            withContext(Dispatchers.IO) {
                return@withContext phoneDataSource.getPhone(imei).let {
                    it?.let {
                        Mapper.dbPhoneToPhone(it)
                    }?:run{
                        null
                    }
                }
            }


    override suspend fun getPhonesBySend(send: Boolean): List<PhoneEntity> = withContext(Dispatchers.IO) {
        return@withContext  phoneDataSource.getPhonesBySend(send).map{
            Mapper.dbPhoneToPhone(it)
        }
    }


    override suspend fun addPhone(phone: PhoneEntity) = withContext(Dispatchers.IO) {
        phoneDataSource.addPhone(Mapper.phoneToDbPhone(phone))
    }

    override suspend fun updatePhone(phone: PhoneEntity) = withContext(Dispatchers.IO) {
        phoneDataSource.updatePhone(Mapper.phoneToDbPhone(phone))
    }

    override suspend fun deleteAllPhone() {
        phoneDataSource.deleteAll()
    }
}