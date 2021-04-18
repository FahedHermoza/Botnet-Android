package com.squareup.picasso.domain.usecase.phone

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.PhoneRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.PhoneEntity

class UpdatePhoneUseCase(private val phoneRepository: PhoneRepository) {

    suspend operator fun invoke(phone: PhoneEntity) = run { phoneRepository.updatePhone(phone)}
}