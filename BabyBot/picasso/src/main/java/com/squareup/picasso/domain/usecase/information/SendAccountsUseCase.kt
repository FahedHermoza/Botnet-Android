package com.squareup.picasso.domain.usecase.information

import com.squareup.picasso.domain.AccountRepository
import com.squareup.picasso.domain.InformationRepository
import com.squareup.picasso.domain.model.AccountEntity

class SendAccountsUseCase(private val informationRepository: InformationRepository) {

    suspend operator fun invoke(list: List<AccountEntity>) = run { informationRepository.setAccounts(list)}
}