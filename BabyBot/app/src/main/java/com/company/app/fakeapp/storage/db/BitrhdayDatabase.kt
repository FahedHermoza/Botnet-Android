package com.company.app.fakeapp.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.company.app.fakeapp.storage.db.imei.Imei
import com.company.app.fakeapp.storage.db.imei.ImeiDao

/**
 * @author Eduardo Medina
 */
@Database(entities = [Imei::class], version = 1)
abstract class BitrhdayDatabase : RoomDatabase() {
    abstract fun imeiDao(): ImeiDao

    companion object {
        private var INSTANCE: BitrhdayDatabase? = null
        private const val DBNAME = "DBBirthday.db"

        fun getInstance(context: Context): BitrhdayDatabase? {
            if (INSTANCE == null) {
                synchronized(BitrhdayDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        BitrhdayDatabase::class.java, DBNAME
                    ).build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}