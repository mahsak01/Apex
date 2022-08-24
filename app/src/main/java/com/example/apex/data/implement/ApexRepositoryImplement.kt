package com.example.apex.data.implement

import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.repository.ApexRepository
import com.example.apex.data.source.ApexDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ApexRepositoryImplement(private val apexLocalDataSource: ApexDataSource) : ApexRepository {

    override fun getApexListInvoice(): Single<List<ApexListHeader>?> =
        apexLocalDataSource.getApexListInvoice()


    override fun getApexListCheque(): Single<List<ApexListHeader>?> =
        apexLocalDataSource.getApexListCheque()

    override fun getApexItem(id: Long): Single<List<ApexItem>?> =
        apexLocalDataSource.getApexItem(id)

    override fun getApexListHeader(
        name: String,
        isInvoice: Boolean
    ): Single<List<ApexListHeader>?> =
        apexLocalDataSource.getApexListHeader(name, isInvoice)


    override fun deleteApexItem(apexItem: ApexItem): Completable =
        apexLocalDataSource.deleteApexItem(apexItem)

    override fun updateApexItem(apexItem: ApexItem): Completable =
        apexLocalDataSource.updateApexItem(apexItem)

    override fun addApexItem(apexItem: ApexItem): Completable =
        apexLocalDataSource.addApexItem(apexItem)

    override fun addApexListHeader(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.addApexListHeader(apexListHeader)

    override fun updateApexListHeader(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.updateApexListHeader(apexListHeader)


    override fun deleteApexListHeader(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.deleteApexListHeader(apexListHeader)
}