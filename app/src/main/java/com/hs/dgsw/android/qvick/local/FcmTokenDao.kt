package com.hs.dgsw.android.qvick.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FcmTokenDao {
    @Query("SELECT * FROM fcm_token_table")
    fun getMembers(): FcmTokenEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(entity: FcmTokenEntity)

    @Update
    fun updateMember(entity: FcmTokenEntity)

    @Delete
    fun deleteMember(entity: FcmTokenEntity)
}