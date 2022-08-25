package com.example.apex.common

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import java.text.DecimalFormat
import java.text.SimpleDateFormat

fun getTimeOfDate(time1: String):Long{
    val date1 = SimpleDateFormat("yyyy/MM/dd").parse(time1)
    return date1.time
}
fun differenceDates(time1: String, time2: String): Long {

    val date1 = SimpleDateFormat("yyyy/MM/dd").parse(time1)
    val date2 = SimpleDateFormat("yyyy/MM/dd").parse(time2)
    val difference: Long = date2.time - date1.time
    if (difference < 0)
        return 0
    return difference / (24 * 60 * 60 * 1000)
}

fun addDates(time1: String, day: Int): String {

    val date1 = SimpleDateFormat("yyyy/MM/dd").parse(time1)
    return "1402/10/05"
}


fun priceInterests(percent: Int, price: Long, apexDay: Long): Long{
    var result=0f
    result=((apexDay / 30).toFloat())
    result*=percent
    result*=price
    result/=100
    return result.toLong()
}

fun apexDay(apexItems: List<ApexItem>,apexListHeader: ApexListHeader): Int {
    var result = 0L
    for (item in apexItems) {
        result += priceInterests(
            apexListHeader.percent,
            item.price.toLong(),
            differenceDates(apexListHeader.date, item.date)
        ) * item.price.toLong()
    }
    return (result / totalPrice(apexItems)).toInt()
}

fun totalPrice(apexItems: List<ApexItem>): Long {
    var result = 0L
    for (item in apexItems) {
        result += item.price.toLong()
    }
    return result
}

fun totalPriceInterest(apexItems: List<ApexItem>,apexListHeader: ApexListHeader): Long {
    var result = 0L
    for (item in apexItems) {
        result += priceInterests(
            apexListHeader.percent,
            item.price.toLong(),
            differenceDates(apexListHeader.date, item.date)

        )
    }
    return result
}

fun getLastPrice(apexListHeader: ApexListHeader, totalPrice: Long): Long {
    return apexListHeader.price.toLong() - totalPrice
}

fun getLastInterest(apexListHeader: ApexListHeader, lastPrice: Long, apexDay: Long): Long {
    return priceInterests(apexListHeader.percent, lastPrice, apexDay)
}

fun getLastDate(apexListHeader: ApexListHeader, totalPrice: Long, lastPrice: Long): String {
    var result = apexListHeader.apexDay.toLong()
    result *= apexListHeader.price.toLong()
    result -= totalPrice
    result /= lastPrice

    return addDates(apexListHeader.date, result.toInt())
}
fun String.beautifyPrice(attachRial: Boolean = true, isNegative: Boolean = false): String {
    val formatter = if (attachRial) DecimalFormat("#,##0" + " ریال") else
        DecimalFormat("#,##0")
    val handle=StringBuilder(formatter.format(this.toDouble()))
    if (isNegative)handle.append(")").insert(0,"(")
    return handle.toString()
}
