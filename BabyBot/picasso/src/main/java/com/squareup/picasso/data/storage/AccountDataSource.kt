package com.squareup.picasso.data.storage

import com.squareup.picasso.data.db.DBAccount

interface AccountDataSource {
    suspend fun accounts(): List<DBAccount>
    suspend fun getAccount(nameEmail: String): DBAccount?
    suspend fun getAccountsBySend(send: Boolean): List<DBAccount>
    suspend fun addAccount(dbAccount: DBAccount)
    suspend fun updateAccount(dbAccount: DBAccount)
    suspend fun deleteAccount(dbAccount: DBAccount)
    suspend fun deleteAll()
}