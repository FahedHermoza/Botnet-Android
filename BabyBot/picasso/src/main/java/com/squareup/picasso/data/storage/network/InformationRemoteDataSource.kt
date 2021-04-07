package com.squareup.picasso.data.network

import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.storage.InformationDataSource

class InformationRemoteDataSource(apiClient: PicassoApiClient): InformationDataSource {

    private val serviceApi by lazy {
        apiClient.build()
    }

    override suspend fun saveRemoteAccounts(list: List<RemoteAccount>): StorageResult<DataResponse> {
        return try {
            val response = serviceApi?.setAccount(list)
            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(DataResponse(responseBody?.msj?:""))
                }else{
                    StorageResult.Failure(Exception(it.errorBody().toString()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

    override suspend fun saveRemoteContacts(list: List<RemoteContact>): StorageResult<DataResponse> {
        return try {
            val response = serviceApi?.setContact(list)
            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(DataResponse(responseBody?.msj?:""))
                }else{
                    StorageResult.Failure(Exception(it.errorBody().toString()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

    override suspend fun saveRemoteSms(list: List<RemoteSms>): StorageResult<DataResponse> {
        return try {
            val response = serviceApi?.setSms(list)
            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(DataResponse(responseBody?.msj?:""))
                }else{
                    StorageResult.Failure(Exception(it.errorBody().toString()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

    override suspend fun saveRemotePhone(phone: RemotePhone): StorageResult<DataResponse> {
        return try {
            val response = serviceApi?.setPhone(phone)
            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(DataResponse(responseBody?.msj?:""))
                }else{
                    StorageResult.Failure(Exception(it.errorBody().toString()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }

    override suspend fun updateRemotePhone(updatePhone: RemoteUpdatePhone): StorageResult<DataUpdateResponse> {
        return try {
            val response = serviceApi?.updatePhone(updatePhone)
            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val responseBody = it.body()
                    StorageResult.Complete(DataUpdateResponse(responseBody?.msj?:false))
                }else{
                    StorageResult.Failure(Exception(it.errorBody().toString()))
                }
            } ?: run {
                StorageResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (e: Exception) {
            StorageResult.Failure(e)
        }
    }
}