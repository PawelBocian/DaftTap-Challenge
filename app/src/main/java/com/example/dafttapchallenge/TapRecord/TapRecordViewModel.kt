package com.example.dafttapchallenge.TapRecord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dafttapchallenge.Entity.TapRecord
import com.example.dafttapchallenge.database.TapRecordDataBase

class TapRecordViewModel(application: Application) : AndroidViewModel(application) {

    val allTapRecords: MutableLiveData<MutableList<TapRecord>> by lazy { MutableLiveData<MutableList<TapRecord>>() }
    val timeStamp: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    var tapRecords: MutableList<TapRecord> = arrayListOf()
    var newTapRecordsToDatabase: MutableList<TapRecord> = arrayListOf()
    private val dataBase: TapRecordDataBase = TapRecordDataBase.getInstance(application)
    var entityTapRecords: MutableList<com.example.dafttapchallenge.database.TapRecord> = dataBase.tapRecordDao().getAllTapRecords()

    init {
        getDatabaseData()
    }

    fun getTapRecords(): MutableLiveData<MutableList<TapRecord>> {
        return allTapRecords
    }

    fun newTapRecord(tapRecord: TapRecord) {
        tapRecords.add(tapRecord)
        newTapRecordsToDatabase.add(tapRecord)
        allTapRecords.value = getTopFive(tapRecords)
    }

    fun getDatabaseData() {
        if (entityTapRecords.size != 0) {
            for (tap in entityTapRecords) {
                tapRecords.add(tap.toEntity())
            }
            allTapRecords.postValue(getTopFive(tapRecords))
        }
    }

    fun insertIntoDatabase() {
        for (taprecord in newTapRecordsToDatabase){
            dataBase.tapRecordDao().insert(taprecord.toDatabaseEntity())
        }
        newTapRecordsToDatabase = arrayListOf()
    }

    // For testing
    fun deleteAllDatabase() {
        dataBase.tapRecordDao().delete()
    }

    fun setTimeStamp(Stamp: String) {
        timeStamp.value = Stamp
    }

    fun getDaisThreshold(): Int {
        when (tapRecords.size > 5) {
            true -> return tapRecords.get(4).score
            false -> return 0
        }
    }

    fun getTopFive(list: MutableList<TapRecord>): MutableList<TapRecord> {
        val topFiveDais: MutableList<TapRecord> = arrayListOf()
        var size: Int = 0

        when (list.size >= 5) {
            true -> size = 4
            false -> size = list.size-1
        }
        list.sortByDescending { it.score }
        if (size != -1) {
            for (i in 0..size) {
                topFiveDais.add(list[i])
            }
        }
        return topFiveDais
    }
}