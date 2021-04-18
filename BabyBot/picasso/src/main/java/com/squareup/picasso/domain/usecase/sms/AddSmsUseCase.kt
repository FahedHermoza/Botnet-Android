package com.squareup.picasso.domain.usecase.sms

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.SmsRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.SmsEntity

class AddSmsUseCase(private val smsRepository: SmsRepository) {

    suspend operator fun invoke(sms: SmsEntity) = run { smsRepository.addSms(sms)}
}