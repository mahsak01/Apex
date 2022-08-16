package com.example.apex.data.repository

import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import io.reactivex.Completable
import io.reactivex.Single

interface ApexRepository {

    fun getApexListInvoice(): Single<List<ApexListHeader>?>

    fun getApexListCheque(): Single<List<ApexListHeader>?>

    fun getApexListItem(id:Long): Single<List<ApexItem>?>

    fun deleteApexListItem(apexItem: ApexItem): Completable

    fun updateApexListItem(apexItem: ApexItem): Completable

    fun addApexListItem(apexItem: ApexItem): Completable

    fun addApexList(apexListHeader: ApexListHeader): Completable

    fun updateApexList(apexListHeader: ApexListHeader): Completable

    fun deleteApexList(apexListHeader: ApexListHeader): Completable


}