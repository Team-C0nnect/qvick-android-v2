package com.hs.dgsw.android.qvick.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fcm_token_table")
data class FcmTokenEntity (
    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "fcmToken")
    val fcmToken: String
)