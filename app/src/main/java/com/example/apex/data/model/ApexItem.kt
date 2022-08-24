package com.example.apex.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Entity(tableName = "ApexItem",
    foreignKeys = [ForeignKey(
        entity = ApexListHeader::class,
        childColumns = ["Apex_list_header_item"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )])
@Parcelize
data class ApexItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val serial: String,
    val date: String,
    val price: Int,
    @ColumnInfo(name = "Apex_list_header_item")
    var ApexListHeaderItem:Long

) : Parcelable {
    @Ignore
    var isShowMenu:Boolean = false

}