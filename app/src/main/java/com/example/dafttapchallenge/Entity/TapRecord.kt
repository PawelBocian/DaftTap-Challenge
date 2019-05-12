package com.example.dafttapchallenge.Entity

import com.example.dafttapchallenge.database.TapRecord

class TapRecord constructor(var result: Int, var stamp : String ){

    var score: Int = result
    var timeStamp: String = stamp

    fun toDatabaseEntity(): com.example.dafttapchallenge.database.TapRecord = TapRecord(0,score,timeStamp)
}




