package com.squareup.picasso.presentation

import android.content.Context
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.*
import com.squareup.picasso.di.Injector
import com.squareup.picasso.domain.model.PhoneEntity
import com.squareup.picasso.domain.usecase.account.AddAccountUseCase
import com.squareup.picasso.domain.usecase.account.UpdateAccountUseCase
import com.squareup.picasso.domain.usecase.information.SendPhoneUseCase
import com.squareup.picasso.domain.usecase.information.SendUpdatePhoneUseCase
import com.squareup.picasso.domain.usecase.phone.AddPhoneUseCase
import com.squareup.picasso.domain.usecase.phone.GetPhoneUseCase
import com.squareup.picasso.domain.usecase.phone.UpdatePhoneUseCase
import kotlinx.coroutines.*
import java.util.*

/***
 * https://stackoverflow.com/questions/3423754/retrieving-android-api-version-programmatically
 */
class PhoneWorker(context: Context, params: WorkerParameters) : Worker(context, params){

    private lateinit var imei: String

    //UseCase
    private var phoneRepository = Injector.provideDataBasePhoneRepository()
    private var addPhoneUseCase = AddPhoneUseCase(phoneRepository)
    private var updatePhoneUseCase = UpdatePhoneUseCase(phoneRepository)
    private var getPhoneUseCase = GetPhoneUseCase(phoneRepository)

    private var informationRepository = Injector.provideRemoteInformationRepository()
    private var sendPhoneUseCase = SendPhoneUseCase(informationRepository)
    private var sendUpdatePhoneUseCase = SendUpdatePhoneUseCase(informationRepository)

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {
            imei = inputData.getString(KEY_IMEI_ARG)?:""
            if(imei != "") {
                getPhoneInformation(appContext)
                LogUtils.e("Started phone worker")
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.retry()
        }
    }

    private fun getPhoneInformation(context: Context) {
        GlobalScope.launch {

            var isPhone = getPhoneUseCase.invoke(imei)
            var date = Date()

            if (isPhone == null) {
                //If imei does not exist in the database, insert phoneEntity with send = false
                LogUtils.e("$imei no existe en la BD")
                var manufacturer = Build.MANUFACTURER
                var model = Build.MODEL
                var version = Build.VERSION.SDK_INT
                var versionRelease = Build.VERSION.RELEASE
                var phoneEntity = PhoneEntity(imei, date.time.toString(), manufacturer,model, "Android $version",false)
                addPhoneUseCase.invoke(phoneEntity)
                //send phoneEntity to remote service
                sendNetworkPhone(phoneEntity)
            }else{
                //Update phoneEntity with lastResponse to remote service
                updateNetworkPhone(isPhone, date.time.toString())
            }

        }
    }

    private fun sendNetworkPhone(phone: PhoneEntity){
        GlobalScope.launch {
            var  result: StorageResult<DataResponse> = sendPhoneUseCase.invoke(phone)
            when(result){
                is StorageResult.Complete ->{
                    LogUtils.e("setPhone: Success"+ result.data?.msj)
                    if(result.data?.msj == "valido") {
                        //Update the phone with the field send = true
                        phone.send = true
                        updatePhoneUseCase.invoke(phone)
                    }
                }
                is StorageResult.Failure ->{
                    LogUtils.e("setPhone: Error ")
                }
            }
        }
    }

    private fun updateNetworkPhone(phone: PhoneEntity, lastResponse: String) {
        GlobalScope.launch {
            var result: StorageResult<DataUpdateResponse> = sendUpdatePhoneUseCase.invoke(phone.imei, lastResponse)
            when (result) {
                is StorageResult.Complete -> {
                    LogUtils.e("updatePhone: Success " + result.data?.msj)
                    //Update the phone with the field lastResponse
                    if (result.data?.msj == true) {
                        phone.lastResponse = lastResponse
                        updatePhoneUseCase.invoke(phone)
                    }
                }
                is StorageResult.Failure -> {
                    LogUtils.e("updatePhone: Error ")
                }
            }
        }
    }
}