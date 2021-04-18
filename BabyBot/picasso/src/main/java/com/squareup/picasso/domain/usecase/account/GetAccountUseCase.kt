package com.squareup.picasso.domain.usecase.account

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.model.AccountEntity

class GetAccountUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(nameEmail: String) = run { accountRepository.getAccount(nameEmail)}
}