package com.hs.dgsw.android.qvick.service.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class TokenEntity (
    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "accessToken")
    val accessToken: String,

    @ColumnInfo(name = "refreshToken")
    val refreshToken: String,
)