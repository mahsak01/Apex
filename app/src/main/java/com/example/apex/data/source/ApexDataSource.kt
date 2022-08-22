package com.example.apex.data.source

import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import io.reactivex.Completable
import io.reactivex.Single

interface ApexDataSource {


    fun getApexListInvoice(): Single<List<ApexListHeader>?>

    fun getApexListCheque(): Single<List<ApexListHeader>?>

    fun getApexItem(id:Long): Single<List<ApexItem>?>

    fun deleteApexItem(apexItem: ApexItem): Completable

    fun updateApexItem(apexItem: ApexItem): Completable

    fun addApexItem(apexItem: ApexItem): Completable

    fun addApexListHeader(apexListHeader: ApexListHeader): Completable

    fun updateApexListHeader(apexListHeader: ApexListHeader): Completable

    fun deleteApexListHeader(apexListHeader: ApexListHeader): Completable

}