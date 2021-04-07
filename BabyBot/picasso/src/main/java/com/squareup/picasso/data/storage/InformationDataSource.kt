package com.squareup.picasso.data.storage

import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.*

interface InformationDataSource {

    suspend fun saveRemoteAccounts(list: List<RemoteAccount>): StorageResult<DataResponse>
    suspend fun saveRemoteContacts(list: List<RemoteContact>): StorageResult<DataResponse>
    suspend fun saveRemoteSms(list: List<RemoteSms>): StorageResult<DataResponse>
    suspend fun saveRemotePhone(phone: RemotePhone): StorageResult<DataResponse>
    suspend fun updateRemotePhone(updatePhone: RemoteUpdatePhone): StorageResult<DataUpdateResponse>
}