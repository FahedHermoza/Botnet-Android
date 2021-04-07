package com.squareup.picasso.di

import android.content.Context
import com.squareup.picasso.data.*
import com.squareup.picasso.data.db.PicassoDatabase
import com.squareup.picasso.data.network.InformationRemoteDataSource
import com.squareup.picasso.data.network.PicassoApiClient
import com.squareup.picasso.data.storage.*
import com.squareup.picasso.data.storage.db.AccountDatabaseDataSource
import com.squareup.picasso.data.storage.db.ContactDatabaseDataSource
import com.squareup.picasso.data.storage.db.PhoneDatabaseDataSource
import com.squareup.picasso.data.storage.db.SmsDatabaseDataSource
import com.squareup.picasso.domain.*

object Injector {

    private val informationDataSource: InformationDataSource = InformationRemoteDataSource(
        PicassoApiClient.getInstance())
    private val informationRemoteRepository: InformationRepository = InformationRemoteRepository(informationDataSource)

    private lateinit var phoneDataSource: PhoneDataSource
    private lateinit var phoneRepository: PhoneRepository

    private lateinit var accountDataSource: AccountDataSource
    private lateinit var accountRepository: AccountRepository

    private lateinit var contactDataSource: ContactDataSource
    private lateinit var contactRepository: ContactRepository

    private lateinit var smsDataSource: SmsDataSource
    private lateinit var smsRepository: SmsRepository

    fun setup(context: Context) {
        PicassoDatabase.getInstance(context)?.let {
            phoneDataSource = PhoneDatabaseDataSource(it)
            phoneRepository = PhoneDatabaseRepository(phoneDataSource)

            accountDataSource = AccountDatabaseDataSource(it)
            accountRepository = AccountDatabaseRepository(accountDataSource)
            contactDataSource = ContactDatabaseDataSource(it)
            contactRepository =
                ContactDatabaseRepository(
                    contactDataSource
                )
            smsDataSource = SmsDatabaseDataSource(it)
            smsRepository =
                SmsDatabaseRepository(
                    smsDataSource
                )
        }
    }

    fun provideRemoteInformationRepository() = informationRemoteRepository
    fun provideDataBasePhoneRepository(): PhoneRepository = phoneRepository
    fun provideDataBaseAccountRepository(): AccountRepository = accountRepository
    fun provideDataBaseContactRepository(): ContactRepository = contactRepository
    fun provideDataBaseSmsRepository(): SmsRepository = smsRepository
}