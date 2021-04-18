package com.squareup.picasso.domain.usecase.phone

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.PhoneRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.PhoneEntity

class GetPhoneUseCase(private val phoneRepository: PhoneRepository) {

    suspend operator fun invoke(imei: String) = run { phoneRepository.getPhone(imei)}
}