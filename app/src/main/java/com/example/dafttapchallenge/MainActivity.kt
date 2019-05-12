package com.example.dafttapchallenge

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dafttapchallenge.Entity.TapRecord
import com.example.dafttapchallenge.TapRecord.TapRecordAdapter
import com.example.dafttapchallenge.TapRecord.TapRecordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 99
    private lateinit var model: TapRecordViewModel
    private var adapter: TapRecordAdapter = TapRecordAdapter(arrayListOf())
    private var timeStamp: String = String()
    private lateinit var tapRecord: TapRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecycledView
        recycledView_tapRecord.layoutManager = LinearLayoutManager(this@MainActivity)
        recycledView_tapRecord.adapter = adapter

        //ViewModel + Room
        model = ViewModelProviders.of(this).get(TapRecordViewModel::class.java)

        //LiveData
        model.getTapRecords().observe(this, Observer<MutableList<TapRecord>> { records ->
            adapter.setData(records) })

        model.timeStamp.observe(this, Observer<String> { actualStamp ->
            timeStamp = actualStamp
        })

        playButton.onClickStartActivity(GameActivity::class)
    }

    private fun <T : Activity> View.onClickStartActivity(activityClass: KClass<T>) = setOnClickListener {
        val intent = Intent(context,activityClass.java)
        intent.putExtra("DAISTHRESHOLD",model.getDaisThreshold())
        model.setTimeStamp(getTimeStamp())
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            tapRecord = TapRecord(data.getIntExtra("SCORE",0),timeStamp)
            model.newTapRecord(tapRecord)
        }
    }

    private fun getTimeStamp() : String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY)
        val currentTimeStamp = dateFormat.format(Date())

        return currentTimeStamp
    }

    override fun onPause() {
        super.onPause()
        model.insertIntoDatabase()
    }
}
