package com.example.apex.data.source.local

import androidx.room.*
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.source.ApexDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ApexLocalDataSource : ApexDataSource {

    @Query("SELECT * FROM ApexListHeader WHERE is_invoice=1")
    override fun getApexListInvoice(): Single<List<ApexListHeader>?>

    @Query("SELECT * FROM ApexListHeader WHERE is_invoice=0")
    override fun getApexListCheque(): Single<List<ApexListHeader>?>

    @Query("SELECT * FROM ApexItem WHERE Apex_list_header_item= :id")
    override fun getApexItem(id: Long): Single<List<ApexItem>?>

    @Query("SELECT * FROM ApexListHeader WHERE name= :name AND is_invoice= :isInvoice")
    override fun getApexListHeader(name: String,isInvoice:Boolean): Single<List<ApexListHeader>?>


    @Delete
    override fun deleteApexItem(apexItem: ApexItem): Completable

    @Delete
    override fun deleteApexListHeader(apexListHeader: ApexListHeader): Completable

    @Update
    override fun updateApexItem(apexItem: ApexItem): Completable

    @Update
    override fun updateApexListHeader(apexListHeader: ApexListHeader): Completable

    @Insert
    override fun addApexItem(apexItem: ApexItem): Completable

    @Insert
    override fun addApexListHeader(apexListHeader: ApexListHeader): Completable


}