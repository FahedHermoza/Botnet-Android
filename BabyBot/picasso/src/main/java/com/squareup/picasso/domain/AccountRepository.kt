package com.squareup.picasso.domain

import com.squareup.picasso.domain.model.AccountEntity

interface AccountRepository {
    suspend fun getAllAccounts(): List<AccountEntity>
    suspend fun getAccount(nameEmail: String): AccountEntity?
    suspend fun getAccountsBySend(send: Boolean): List<AccountEntity>
    suspend fun addAccount(account: AccountEntity)
    suspend fun updateAccount(account: AccountEntity)
    suspend fun deleteAllAccount()
}