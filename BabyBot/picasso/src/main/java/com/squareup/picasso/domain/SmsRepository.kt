package com.squareup.picasso.domain

import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.SmsEntity

interface SmsRepository {
    suspend fun getAllSms(): List<SmsEntity>
    suspend fun getSms(id: String): SmsEntity?
    suspend fun getSmsListBySend(send: Boolean): List<SmsEntity>
    suspend fun addSms(sms: SmsEntity)
    suspend fun updateSms(sms: SmsEntity)
    suspend fun deleteAllSms()
}