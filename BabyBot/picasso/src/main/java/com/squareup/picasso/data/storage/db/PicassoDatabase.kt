package com.squareup.picasso.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DBAccount::class, DBContact::class, DBSms::class, DBPhone::class], version = 1)
abstract class PicassoDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun contactDao(): ContactDao
    abstract fun smsDao(): SmsDao
    abstract fun phoneDao(): PhoneDao

    companion object {
        private var INSTANCE: PicassoDatabase? = null
        private const val DBNAME = "DBPicasso.db"

        fun getInstance(context: Context): PicassoDatabase? {
            if (INSTANCE == null) {
                synchronized(PicassoDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        PicassoDatabase::class.java, DBNAME
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