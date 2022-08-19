package com.example.apex.common

import java.text.SimpleDateFormat
import kotlin.math.abs

fun differenceDates(): String {

    val date1 = SimpleDateFormat("yyyy/MM/dd").parse("1401/06/01")
    val date2 = SimpleDateFormat("yyyy/MM/dd").parse("1401/06/10")
    val difference: Long = abs(date1.time - date2.time)
    val differenceDates = difference / (24 * 60 * 60 * 1000)
    return differenceDates.toString()
}