package com.example.apex.data.source.local

import androidx.room.*
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.source.ApexDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ApexLocalDataSource:ApexDataSource {

    @Query("SELECT * FROM ApexListHeader WHERE is_invoice=1")
    override fun getApexListInvoice(): Single<List<ApexListHeader>?>
    @Query("SELECT * FROM ApexListHeader WHERE is_invoice=0")
    override fun getApexListCheque(): Single<List<ApexListHeader>?>

    @Query("SELECT * FROM ApexItem WHERE Apex_list_header_item= :id")
    override fun getApexListItem(id: Long): Single<List<ApexItem>?>

    @Delete
    override fun deleteApexListItem(apexItem: ApexItem): Completable

    @Query("delete from ApexItem WHERE Apex_list_header_item= :id")
    override fun deleteApexList(id: Long): Completable

    @Update
    override fun updateApexListItem(apexItem: ApexItem): Completable

    @Update
    override fun updateApexList(apexListHeader: ApexListHeader): Completable

    @Insert
    override fun addApexListItem(apexItem: ApexItem): Completable

    @Insert
    override fun addApexList(apexListHeader: ApexListHeader): Completable


}