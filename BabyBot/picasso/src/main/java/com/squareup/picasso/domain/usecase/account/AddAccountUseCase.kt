package com.squareup.picasso.domain.usecase.account

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.model.AccountEntity

class AddAccountUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(account: AccountEntity) = run { accountRepository.addAccount(account)}
}