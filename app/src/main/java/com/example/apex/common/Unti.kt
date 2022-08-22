package com.example.apex.common

import java.text.SimpleDateFormat

fun differenceDates(time1: String, time2: String): Long {

    val date1 = SimpleDateFormat("yyyy/MM/dd").parse(time1)
    val date2 = SimpleDateFormat("yyyy/MM/dd").parse(time2)
    val difference: Long = date2.time - date1.time
    if (difference < 0)
        return 0
    return difference / (24 * 60 * 60 * 1000)
}

fun priceInterests(percent: Int, price: Int, apexDay: Long): Int {
    return ((apexDay / 30) * (percent / 100) * price).toInt()
}