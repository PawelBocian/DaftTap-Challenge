package com.example.dafttapchallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(TapRecord::class)], version = 1)
abstract class TapRecordDataBase: RoomDatabase() {

    abstract fun tapRecordDao() : TapRecordDao

    companion object{
        private var INSTANCE: TapRecordDataBase? = null
        fun getInstance(context: Context): TapRecordDataBase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, TapRecordDataBase::class.java, "tapRecordsDb")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as TapRecordDataBase
        }
    }
}
