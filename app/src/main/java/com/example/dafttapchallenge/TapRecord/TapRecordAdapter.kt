package com.example.dafttapchallenge.TapRecord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dafttapchallenge.Entity.TapRecord
import com.example.dafttapchallenge.R

class TapRecordAdapter(var tapRecords: MutableList<TapRecord>): RecyclerView.Adapter<TapRecordViewHolder>() {

    override fun getItemCount(): Int {
        return tapRecords.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TapRecordViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.taprecord_item, parent, false)
        return TapRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: TapRecordViewHolder, position: Int) = holder.bind(tapRecords[position])

    fun setData( data: MutableList<TapRecord>){
        this.tapRecords = data
        notifyDataSetChanged()
    }

}