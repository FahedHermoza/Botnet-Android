package com.squareup.picasso.domain

import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity

interface ContactRepository {
    suspend fun getAllContacts(): List<ContactEntity>
    suspend fun getContact(contactId: String, dateLastUpdate: String): ContactEntity?
    suspend fun getContactsBySend(send: Boolean): List<ContactEntity>
    suspend fun addContact(contact: ContactEntity)
    suspend fun updateContact(contact: ContactEntity)
    suspend fun deleteAllContact()
}