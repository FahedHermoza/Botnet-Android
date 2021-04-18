package com.squareup.picasso.domain.usecase.sms

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.SmsRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.SmsEntity

class GetSmsUseCase(private val smsRepository: SmsRepository) {

    suspend operator fun invoke(id: String) = run { smsRepository.getSms(id)}
}