package com.squareup.picasso.data

import com.squareup.picasso.data.storage.ContactDataSource
import com.squareup.picasso.domain.ContactRepository
import com.squareup.picasso.domain.model.ContactEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactDatabaseRepository(private val contactDataSource: ContactDataSource):
    ContactRepository {

    override suspend fun getAllContacts(): List<ContactEntity> = withContext(Dispatchers.IO) {
        return@withContext contactDataSource.contacts().map {
            Mapper.dbContactToContact(it)
        }
    }

    override suspend fun getContact(contactId: String, dateLastUpdate: String): ContactEntity? = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            return@withContext contactDataSource.getContact(contactId, dateLastUpdate).let {
                it?.let {
                    Mapper.dbContactToContact(it)
                }?:run{
                    null
                }
            }
        }
    }

    override suspend fun getContactsBySend(send: Boolean): List<ContactEntity> = withContext(Dispatchers.IO) {
        return@withContext  contactDataSource.getContactsBySend(send).map{
            Mapper.dbContactToContact(it)
        }
    }

    override suspend fun addContact(contact: ContactEntity) = withContext(Dispatchers.IO) {
        contactDataSource.addContact(Mapper.contactToDbContact(contact))
    }

    override suspend fun updateContact(contact: ContactEntity) = withContext(Dispatchers.IO) {
        contactDataSource.updateContact(Mapper.contactToDbContact(contact))
    }

    override suspend fun deleteAllContact() = withContext(Dispatchers.IO) {
        contactDataSource.deleteAll()
    }
}