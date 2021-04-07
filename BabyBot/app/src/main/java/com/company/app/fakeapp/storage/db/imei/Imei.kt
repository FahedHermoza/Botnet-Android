package com.company.app.fakeapp.storage.db.imei

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "imei_table", primaryKeys =["idUnique"])
class Imei(@ColumnInfo(name = "idUnique") val idUnique: String)