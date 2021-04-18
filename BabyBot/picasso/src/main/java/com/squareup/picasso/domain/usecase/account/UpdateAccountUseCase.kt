package com.squareup.picasso.domain.usecase.account

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.model.AccountEntity

class UpdateAccountUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(account: AccountEntity) = run { accountRepository.updateAccount(account)}
}