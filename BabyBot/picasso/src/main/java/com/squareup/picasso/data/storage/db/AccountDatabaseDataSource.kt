package com.squareup.picasso.data.storage.db

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.PicassoDatabase
import com.squareup.picasso.data.storage.AccountDataSource

class AccountDatabaseDataSource(private val database: PicassoDatabase): AccountDataSource {

    private val accountDao = database.accountDao()

    override suspend fun accounts(): List<DBAccount> = accountDao.accounts()

    override suspend fun getAccount(nameEmail: String): DBAccount? = accountDao.getAccount(nameEmail)

    override suspend fun getAccountsBySend(send: Boolean): List<DBAccount> = accountDao.getAccountsBySend(send)

    override suspend fun addAccount(dbAccount: DBAccount) {
        accountDao.addAccount(dbAccount)
    }

    override suspend fun updateAccount(dbAccount: DBAccount) {
        accountDao.updateAccount(dbAccount)
    }

    override suspend fun deleteAccount(dbAccount: DBAccount) {
        accountDao.deleteAccount(dbAccount)
    }

    override suspend fun deleteAll() {
        accountDao.deleteAll()
    }
}