package com.example.popup.utils

import com.example.popup.model.items
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*


class XAxisValueFormatter(var dateList: List<items>?): ValueFormatter() {

//    var datesList: List<String>? = null
//
//    fun ClaimsXAxisValueFormatter(arrayOfDates: List<String>?) {
//        datesList = arrayOfDates
//    }


    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
    /*
    Depends on the position number on the X axis, we need to display the label, Here, this is the logic to convert the float value to integer so that I can get the value from array based on that integer and can convert it to the required value here, month and date as value. This is required for my data to show properly, you can customize according to your needs.
    */
        var position = Math.round(value)
        val sdf = SimpleDateFormat("MMM dd")
        if (value > 1 && value < 2) {
            position = 0
        } else if (value > 2 && value < 3) {
            position = 1
        } else if (value > 3 && value < 4) {
            position = 2
        } else if (value > 4 && value <= 5) {
            position = 3
        }

        return if (position < dateList!!.size) sdf.format(
            Date(
                dateList!![position].createdAt
            )
        ) else ""
    }


}