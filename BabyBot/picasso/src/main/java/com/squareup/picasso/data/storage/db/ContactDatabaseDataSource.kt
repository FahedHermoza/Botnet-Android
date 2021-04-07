package com.squareup.picasso.data.storage.db

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBContact
import com.squareup.picasso.data.db.PicassoDatabase
import com.squareup.picasso.data.storage.AccountDataSource
import com.squareup.picasso.data.storage.ContactDataSource

class ContactDatabaseDataSource(private val database: PicassoDatabase): ContactDataSource {

    private val contactDao = database.contactDao()

    override suspend fun contacts(): List<DBContact> = contactDao.contacts()

    override suspend fun getContact(contactId: String, dateLastUpdate: String): DBContact? = contactDao.getContact(contactId, dateLastUpdate)

    override suspend fun getContactsBySend(send: Boolean): List<DBContact> = contactDao.getContactsBySend(send)

    override suspend fun addContact(dbContact: DBContact) {
        contactDao.addContact(dbContact)
    }

    override suspend fun updateContact(dbContact: DBContact) {
        contactDao.updateContact(dbContact)
    }

    override suspend fun deleteContact(dbContact: DBContact) {
        contactDao.deleteContact(dbContact)
    }

    override suspend fun deleteAll() {
        contactDao.deleteAll()
    }
}