package com.squareup.picasso.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_account")
data class DBAccount(
    @ColumnInfo(name = "imei") val imei: String?,
    @PrimaryKey @ColumnInfo(name = "nameEmail") val nameEmail: String,
    @ColumnInfo(name = "send") val send: Boolean?
)