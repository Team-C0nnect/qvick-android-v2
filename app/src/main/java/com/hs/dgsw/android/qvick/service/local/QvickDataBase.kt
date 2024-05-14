package com.hs.dgsw.android.qvick.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TokenEntity::class, FcmTokenEntity::class], version = 3)
abstract class QvickDataBase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun fcmTokenDao(): FcmTokenDao

    companion object {

        private var instance: QvickDataBase? = null

        @Synchronized
        fun getInstance(context: Context): QvickDataBase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                context.applicationContext,
                QvickDataBase::class.java, "database"
                ).build()
            }
            return instance
        }

        fun getInstanceOrNull(): QvickDataBase? {
            return instance
        }
    }
}