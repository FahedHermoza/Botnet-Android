package com.squareup.picasso.domain.usecase.information

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.InformationRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity

class SendContactsUseCase(private val informationRepository: InformationRepository) {

    suspend operator fun invoke(list: List<ContactEntity>) = run { informationRepository.setContacts(list)}
}