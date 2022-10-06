package com.example.apex.data.source

import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.model.GetAddDateInformation
import com.example.apex.data.model.GetDiffDateInformation
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Query

interface ApexDataSource {


    fun getApexListInvoice(): Single<List<ApexListHeader>?>

    fun getApexListCheque(): Single<List<ApexListHeader>?>

    fun getApexItem(id:Long): Single<List<ApexItem>?>

    fun getApexListHeader(name:String,isInvoice:Boolean): Single<List<ApexListHeader>?>

    fun deleteApexItem(apexItem: ApexItem): Completable

    fun updateApexItem(apexItem: ApexItem): Completable

    fun addApexItem(apexItem: ApexItem): Completable

    fun addApexListHeader(apexListHeader: ApexListHeader): Completable

    fun updateApexListHeader(apexListHeader: ApexListHeader): Completable

    fun deleteApexListHeader(apexListHeader: ApexListHeader): Completable

    fun getDiffDate(fromDate:String, toDate:String):Single<GetDiffDateInformation>

    fun getAddDate(date:String,addDay:String):Single<GetAddDateInformation>

    fun getDate():Single<List<String>>


}