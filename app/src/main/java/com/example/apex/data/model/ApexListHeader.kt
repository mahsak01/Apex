package com.example.apex.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Entity(tableName = "ApexListHeader")
@Parcelize
data class ApexListHeader(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    val name: String,
    @ColumnInfo(name = "account_name")
    val accountName: String,
    val percent: Int,
    @ColumnInfo(name = "apex_day")
    val apexDay: Int,
    val price:String,
    @ColumnInfo(name = "total_price")
    var totalPrice: String,
    @ColumnInfo(name = "is_invoice")
    var isInvoice: Boolean,
    @ColumnInfo(name = "number_item")
    var numberItem: Int,
    val description:String
) : Parcelable {
    @Ignore
    var isShowMenu:Boolean = false
}