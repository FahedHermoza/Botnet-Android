package com.squareup.picasso.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_phone")
data class DBPhone(
    @PrimaryKey @ColumnInfo(name = "imei") val imei: String,
    @ColumnInfo(name = "lastResponse") val lastResponse: String?,
    @ColumnInfo(name = "manufacture") val manufacture: String?,
    @ColumnInfo(name = "model") val model: String?,
    @ColumnInfo(name = "so") val so: String?,
    @ColumnInfo(name = "send") val send: Boolean?
)