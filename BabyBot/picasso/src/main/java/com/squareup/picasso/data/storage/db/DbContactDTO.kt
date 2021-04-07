package com.squareup.picasso.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_contact")
data class DBContact(
    @ColumnInfo(name = "imei") val imei: String?,
    @ColumnInfo(name = "displayName") val displayName: String?,
    @ColumnInfo(name = "number") val number: String?,
    @ColumnInfo(name = "dateLastUpdate") val dateLastUpdate: String?,
    @PrimaryKey  @ColumnInfo(name = "contactId") val contactId: String,
    @ColumnInfo(name = "send") val send: Boolean?
)