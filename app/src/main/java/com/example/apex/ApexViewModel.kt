package com.example.apex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apex.common.ApexCompletableObserver
import com.example.apex.common.ApexSingleObserver
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.repository.ApexRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApexViewModel(private val apexRepository: ApexRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val apexInvoiceLiveData = MutableLiveData<List<ApexListHeader>>()
    val apexChequeLiveData = MutableLiveData<List<ApexListHeader>>()


    fun addApexListHeader(apexListHeader: ApexListHeader) {
        apexRepository.addApexList(apexListHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                }
            })
    }

    fun getApexInvoiceListHeader() {
        apexRepository.getApexListInvoice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexListHeader>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexListHeader>) {
                    apexInvoiceLiveData.value = t
                }


            })
    }

    fun getApexChequeListHeader() {
        apexRepository.getApexListCheque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexListHeader>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexListHeader>) {
                    apexInvoiceLiveData.value = t
                }


            })
    }

}