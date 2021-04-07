package com.squareup.picasso.data.storage

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBContact

interface ContactDataSource {
    suspend fun contacts(): List<DBContact>
    suspend fun getContact(contactId: String, dateLastUpdate: String): DBContact?
    suspend fun getContactsBySend(send: Boolean): List<DBContact>
    suspend fun addContact(dbContact: DBContact)
    suspend fun updateContact(dbContact: DBContact)
    suspend fun deleteContact(dbContact: DBContact)
    suspend fun deleteAll()
}