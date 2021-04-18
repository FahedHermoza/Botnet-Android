package com.squareup.picasso.domain.usecase.contact

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.ContactRepository
import com.squareup.picasso.domain.model.AccountEntity

class GetContactBySendUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke(send: Boolean) = run { contactRepository.getContactsBySend(send)}
}