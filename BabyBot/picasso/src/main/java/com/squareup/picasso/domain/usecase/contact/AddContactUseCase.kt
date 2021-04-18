package com.squareup.picasso.domain.usecase.contact

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.ContactRepository
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity

class AddContactUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke(contact: ContactEntity) = run { contactRepository.addContact(contact)}
}