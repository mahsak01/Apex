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

    override fun getApexListItem(id: Long): Single<List<ApexItem>?> =
        apexLocalDataSource.getApexListItem(id)

    override fun deleteApexListItem(apexItem: ApexItem): Completable =
        apexLocalDataSource.deleteApexListItem(apexItem)

    override fun updateApexListItem(apexItem: ApexItem): Completable =
      apexLocalDataSource.updateApexListItem(apexItem)

    override fun addApexListItem(apexItem: ApexItem): Completable =
        apexLocalDataSource.addApexListItem(apexItem)

    override fun addApexList(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.addApexList(apexListHeader)

    override fun updateApexList(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.updateApexList(apexListHeader)


    override fun deleteApexList(apexListHeader: ApexListHeader): Completable =
        apexLocalDataSource.deleteApexList(apexListHeader.id)
}