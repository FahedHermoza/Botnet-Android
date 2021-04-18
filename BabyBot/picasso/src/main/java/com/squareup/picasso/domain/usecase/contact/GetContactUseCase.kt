package com.squareup.picasso.domain.usecase.contact

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.ContactRepository
import com.squareup.picasso.domain.model.AccountEntity

class GetContactUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke(contactId: String, dateLastUpdate: String) =
            run { contactRepository.getContact(contactId, dateLastUpdate)}
}