package com.squareup.picasso.data

import com.squareup.picasso.data.storage.AccountDataSource
import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.model.AccountEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountDatabaseRepository(private val accountDataSource: AccountDataSource): AccountRepository {
    override suspend fun getAllAccounts(): List<AccountEntity>  = withContext(Dispatchers.IO) {
        return@withContext accountDataSource.accounts().map {
            Mapper.dbAccountToAccount(it)
        }
    }

    override suspend fun getAccount(nameEmail: String): AccountEntity? =
        withContext(Dispatchers.IO) {
            return@withContext accountDataSource.getAccount(nameEmail).let {
                it?.let {
                    Mapper.dbAccountToAccount(it)
                }?:run{
                    null
                }
            }
        }

    override suspend fun getAccountsBySend(send: Boolean): List<AccountEntity> = withContext(Dispatchers.IO) {
        return@withContext  accountDataSource.getAccountsBySend(send).map{
            Mapper.dbAccountToAccount(it)
        }
    }

    override suspend fun addAccount(account: AccountEntity) = withContext(Dispatchers.IO) {
        accountDataSource.addAccount(Mapper.accountToDbAccount(account))
    }

    override suspend fun updateAccount(account: AccountEntity) = withContext(Dispatchers.IO) {
        accountDataSource.updateAccount(Mapper.accountToDbAccount(account))
    }

    override suspend fun deleteAllAccount() = withContext(Dispatchers.IO) {
        accountDataSource.deleteAll()
    }
}