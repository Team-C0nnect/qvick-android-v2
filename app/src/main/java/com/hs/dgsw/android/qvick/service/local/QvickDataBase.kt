package com.hs.dgsw.android.qvick.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [TokenEntity::class], version = 4)
abstract class QvickDataBase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {

        private var instance: QvickDataBase? = null

        @Synchronized
        fun getInstance(context: Context): QvickDataBase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    QvickDataBase::class.java, "database")
//                    .addMigrations(MIGRATION_3_4)
                    .build()
            }
            return instance
        }

//        private val MIGRATION_3_4 = object : Migration(3,4){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE 'token_table' ADD COLUMN 'accessToken' INTEGER" )
//            }
//        }

        fun getInstanceOrNull(): QvickDataBase? {
            return instance
        }
    }
}