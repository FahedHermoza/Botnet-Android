package com.squareup.picasso.domain.usecase.information

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.InformationRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.SmsEntity

class SendSmsUseCase(private val informationRepository: InformationRepository) {

    suspend operator fun invoke(list: List<SmsEntity>) = run { informationRepository.setSms(list)}
}