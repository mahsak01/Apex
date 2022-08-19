package com.example.apex.common

enum class NamePage(private val s: String) {
    CHEQUE("چک"),INVOICE("فاکتور");
    fun getValue():String{
        return s
    }
}