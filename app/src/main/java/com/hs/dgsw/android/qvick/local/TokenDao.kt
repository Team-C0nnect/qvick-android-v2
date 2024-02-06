package com.hs.dgsw.android.qvick.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TokenDao {
    @Query("SELECT * FROM token_table")
    fun getMembers(): TokenEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(entity: TokenEntity)

    @Update
    fun updateMember(entity: TokenEntity)

    @Delete
    fun deleteMember(entity: TokenEntity)
}