package com.squareup.picasso.data

import com.squareup.picasso.data.db.DBAccount
import com.squareup.picasso.data.db.DBContact
import com.squareup.picasso.data.db.DBPhone
import com.squareup.picasso.data.db.DBSms
import com.squareup.picasso.data.network.RemoteAccount
import com.squareup.picasso.data.network.RemoteContact
import com.squareup.picasso.data.network.RemotePhone
import com.squareup.picasso.data.network.RemoteSms
import com.squareup.picasso.domain.model.AccountEntity
import com.squareup.picasso.domain.model.ContactEntity
import com.squareup.picasso.domain.model.PhoneEntity
import com.squareup.picasso.domain.model.SmsEntity

/**
 * https://kotlinlang.org/docs/reference/collection-transformations.html
 * http://modelmapper.org/
 */
object Mapper {

    //TODO convertir entidad a DTO y DTO a entidad

    fun dbAccountToAccount(dbAccount: DBAccount):AccountEntity = AccountEntity(
        dbAccount.imei?:"", dbAccount.nameEmail, dbAccount.send?:false
    )

    fun accountToDbAccount(account: AccountEntity):DBAccount = DBAccount(account.imei,account.nameEmail,
        account.send)

    fun accountToRemoteAccount(account: AccountEntity):RemoteAccount = RemoteAccount(account.imei,account.nameEmail)

    fun dbContactToContact(dbContact: DBContact):ContactEntity = ContactEntity(
        dbContact.imei?:"", dbContact.displayName?:"", dbContact.number?:"",
        dbContact.dateLastUpdate?:"", dbContact.contactId, dbContact.send?:false
    )

    fun contactToDbContact(contact: ContactEntity):DBContact = DBContact(contact.imei,contact.displayName,
        contact.number, contact.dateLastUpdate, contact.contactId, contact.send)

    fun contactToRemoteContact(contact: ContactEntity):RemoteContact = RemoteContact(
        contact.imei,contact.displayName, contact.number, contact.dateLastUpdate)

    fun dbSmsToSms(dbSMS: DBSms):SmsEntity = SmsEntity(
        dbSMS.imei?:"", dbSMS.address?:"", dbSMS.body?:"",
        dbSMS.date?:"", dbSMS.id, dbSMS.send?:false
    )

    fun smsToDbSms(sms: SmsEntity):DBSms = DBSms(sms.imei, sms.address, sms.body, sms.date, sms._id, sms.send)

    fun smsToRemoteSms(sms: SmsEntity): RemoteSms = RemoteSms(
        sms.imei, sms.address, sms.body, sms.date)

    fun dbPhoneToPhone(dbPhone: DBPhone):PhoneEntity = PhoneEntity(
            dbPhone.imei?:"", dbPhone.lastResponse?:"", dbPhone.manufacture?:"",
            dbPhone.model?:"", dbPhone.so?:"", dbPhone.send?:false
    )

    fun phoneToDbPhone(phone: PhoneEntity):DBPhone = DBPhone(phone.imei, phone.lastResponse, phone.manufacture, phone.model, phone.so, phone.send)

    fun smsToRemoteSms(phone: PhoneEntity): RemotePhone = RemotePhone(
            phone.imei, phone.lastResponse, phone.manufacture, phone.model, phone.so)
}