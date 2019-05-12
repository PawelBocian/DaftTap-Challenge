package com.example.dafttapchallenge.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TapRecordDao {

    @Query("SELECT * FROM TAPRECORDS ORDER BY score ASC" )
    fun getAllTapRecords() : MutableList<TapRecord>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(record: TapRecord)

    @Query("DELETE FROM TAPRECORDS")
    fun delete()
}