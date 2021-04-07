package com.squareup.picasso.domain

import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.RemoteContact
import com.squareup.picasso.data.network.DataResponse
import com.squareup.picasso.data.network.DataUpdateResponse
import com.squareup.picasso.data.network.RemoteSms
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.PhoneEntity
import com.squareup.picasso.domain.model.SmsEntity

interface InformationRepository {
    suspend fun setAccounts(list: List<AccountEntity>): StorageResult<DataResponse>
    suspend fun setContacts(list: List<ContactEntity>): StorageResult<DataResponse>
    suspend fun setSms(list: List<SmsEntity>): StorageResult<DataResponse>
    suspend fun setPhone(phone: PhoneEntity): StorageResult<DataResponse>
    suspend fun updatePhone(imei: String, lastResponse: String): StorageResult<DataUpdateResponse>
}