package com.example.dafttapchallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dafttapchallenge.Entity.TapRecord

@Entity(tableName = "TAPRECORDS")
class TapRecord (

    @PrimaryKey(autoGenerate = true) val id: Int,
    val score: Int,
    var timeStamp: String
) {
    fun toEntity() = TapRecord(score,timeStamp)

}