package com.squareup.picasso.domain

import com.squareup.picasso.domain.model.PhoneEntity

interface PhoneRepository {
    suspend fun getAllPhones(): List<PhoneEntity>
    suspend fun getPhone(imei: String): PhoneEntity?
    suspend fun getPhonesBySend(send: Boolean): List<PhoneEntity>
    suspend fun addPhone(phone: PhoneEntity)
    suspend fun updatePhone(phone: PhoneEntity)
    suspend fun deleteAllPhone()
}