package com.example.apex.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Entity(tableName = "ApexItem",
    foreignKeys = [ForeignKey(
        entity = ApexListHeader::class,
        childColumns = ["Apex_list_header_item"],
        parentColumns = ["id"]
    )])
@Parcelize
data class ApexItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val serial: String,
    val date: Int,
    @ColumnInfo(name = "apex_day")
    val apexDay: Int,
    val price: Int,
    @ColumnInfo(name = "price_interest")
    val priceInterest: Int,
    @ColumnInfo(name = "Apex_list_header_item")
    val ApexListHeaderItem:Long

) : Parcelable {


}