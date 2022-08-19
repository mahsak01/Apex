package com.example.apex.common

enum class MenuStatus(val i: Int) {
    SHOW(0),
    HIDE(1);

    fun getValue(): Int {
        return i
    }
}