package com.squareup.picasso.domain.usecase.information

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.InformationRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.PhoneEntity
import com.squareup.picasso.domain.model.SmsEntity

class SendPhoneUseCase(private val informationRepository: InformationRepository) {

    suspend operator fun invoke(phone: PhoneEntity) = run { informationRepository.setPhone(phone)}
}