package com.squareup.picasso.domain.usecase.account

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.model.AccountEntity

class GetAccountBySendUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(send: Boolean) = run { accountRepository.getAccountsBySend(send)}
}