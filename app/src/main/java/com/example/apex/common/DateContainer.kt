package com.example.apex.common


object DateContainer {
    var shortDate: String? = null
    var smallDate: String? = null
    var longDate: String? = null

    fun update(shortDate: String, smallDate: String, longDate: String) {
        this.shortDate = shortDate
        this.smallDate = smallDate
        this.longDate = longDate
    }
}