package com.example.dafttapchallenge.TapRecord

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dafttapchallenge.Entity.TapRecord
import android.animation.AnimatorInflater
import android.annotation.SuppressLint

import kotlinx.android.synthetic.main.taprecord_item.view.*

class TapRecordViewHolder (itemview: View): RecyclerView.ViewHolder(itemview) {

    @SuppressLint("SetTextI18n")
    fun bind(tapRecord: TapRecord) = with(itemView) {
        pointsTextField.text = "Score : " + tapRecord.score.toString()
        timestampTextField.text = "Date : " + tapRecord.timeStamp
    }
}