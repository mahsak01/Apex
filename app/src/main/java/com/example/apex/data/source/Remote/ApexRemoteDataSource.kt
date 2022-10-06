package com.example.apex.data.source.Remote

import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.model.GetAddDateInformation
import com.example.apex.data.model.GetDiffDateInformation
import com.example.apex.data.source.ApexDataSource
import com.example.apex.service.http.ApiServiceTiana
import io.reactivex.Completable
import io.reactivex.Single

class ApexRemoteDataSource(private val apiServiceTiana: ApiServiceTiana) : ApexDataSource {
    override fun getApexListInvoice(): Single<List<ApexListHeader>?> {
        TODO("Not yet implemented")
    }

    override fun getApexListCheque(): Single<List<ApexListHeader>?> {
        TODO("Not yet implemented")
    }

    override fun getApexItem(id: Long): Single<List<ApexItem>?> {
        TODO("Not yet implemented")
    }

    override fun getApexListHeader(
        name: String,
        isInvoice: Boolean
    ): Single<List<ApexListHeader>?> {
        TODO("Not yet implemented")
    }

    override fun deleteApexItem(apexItem: ApexItem): Completable {
        TODO("Not yet implemented")
    }

    override fun updateApexItem(apexItem: ApexItem): Completable {
        TODO("Not yet implemented")
    }

    override fun addApexItem(apexItem: ApexItem): Completable {
        TODO("Not yet implemented")
    }

    override fun addApexListHeader(apexListHeader: ApexListHeader): Completable {
        TODO("Not yet implemented")
    }

    override fun updateApexListHeader(apexListHeader: ApexListHeader): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteApexListHeader(apexListHeader: ApexListHeader): Completable {
        TODO("Not yet implemented")
    }

    override fun getDiffDate(fromDate: String, toDate: String): Single<GetDiffDateInformation> =
        apiServiceTiana.getDiffDate(fromDate, toDate)

    override fun getAddDate(date: String, addDay: String): Single<GetAddDateInformation> =
        apiServiceTiana.getAddDate(date, addDay)

    override fun getDate(): Single<List<String>> = apiServiceTiana.getDate()


}