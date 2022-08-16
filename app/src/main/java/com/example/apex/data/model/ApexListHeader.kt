package com.example.apex.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
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
    @ColumnInfo(name = "total_price")
    val totalPrice: Int,
    @ColumnInfo(name = "is_invoice")
    var isInvoice: Boolean,
    val description:String
) : Parcelable {


}