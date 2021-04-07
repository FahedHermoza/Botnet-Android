package com.squareup.picasso.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_sms")
data class DBSms(
    @ColumnInfo(name = "imei") val imei: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "date") val date: String?,
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "send") val send: Boolean?
)