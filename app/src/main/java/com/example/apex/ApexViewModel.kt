package com.example.apex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apex.common.ApexCompletableObserver
import com.example.apex.common.ApexSingleObserver
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.repository.ApexRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApexViewModel(private val apexRepository: ApexRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val apexListHeaderLiveData = MutableLiveData<List<ApexListHeader>>()
    val apexItemsLiveData = MutableLiveData<List<ApexItem>>()
    val addApexListHeaderLiveData = MutableLiveData<List<ApexListHeader>>()


    fun searchApexItem(apexItem: ApexItem): Boolean {
        for (item in apexItemsLiveData.value!!)
            if (item.serial == apexItem.serial)
                return false
        return true
    }

    fun searchApexListHeader(apexListHeader: ApexListHeader): Boolean {
        if (apexListHeaderLiveData.value != null && apexListHeaderLiveData.value!!.isNotEmpty())
            for (item in apexListHeaderLiveData.value!!)
                if (item.name == apexListHeader.name)
                    return false
        return true
    }


    fun getApexItems(apexListHeader: ApexListHeader) {
        apexRepository.getApexItem(apexListHeader.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexItem>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexItem>) {
                    apexItemsLiveData.value = t
                }
            })
    }


    fun getApexListHeader(apexListHeader: ApexListHeader) {
        apexRepository.getApexListHeader(apexListHeader.name, apexListHeader.isInvoice)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexListHeader>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexListHeader>) {
                    addApexListHeaderLiveData.value = t
                }
            })
    }

    fun addApexListHeader(apexListHeader: ApexListHeader) {
        apexRepository.addApexListHeader(apexListHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    getApexListHeader(apexListHeader)
                }
            })
    }


    fun updateApexListHeader(apexListHeader: ApexListHeader) {
        apexRepository.updateApexListHeader(apexListHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    getApexListHeader(apexListHeader)
                }
            })
    }

    fun updateApexItem(apexItem: ApexItem, apexListHeader: ApexListHeader) {
        apexRepository.updateApexItem(apexItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    getApexItems(apexListHeader)
                }
            })
    }

    fun getApexInvoiceListHeader() {
        apexRepository.getApexListInvoice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexListHeader>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexListHeader>) {
                    apexListHeaderLiveData.value = t
                }
            })
    }

    fun getApexChequeListHeader() {
        apexRepository.getApexListCheque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexSingleObserver<List<ApexListHeader>?>(compositeDisposable) {
                override fun onSuccess(t: List<ApexListHeader>) {
                    apexListHeaderLiveData.value = t
                }


            })
    }

    fun addApexItem(apexItem: ApexItem, apexListHeader: ApexListHeader) {
        apexItem.ApexListHeaderItem = apexListHeader.id
        apexRepository.addApexItem(apexItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    apexListHeader.numberItem++
                    apexListHeader.totalPrice =
                        (apexListHeader.totalPrice.toLong() + apexItem.price.toLong()).toString()
                    updateApexListHeader(apexListHeader)
                    getApexInvoiceListHeader()
                    getApexItems(apexListHeader)
                }
            })
    }

    fun deleteApexItem(apexItem: ApexItem, apexListHeader: ApexListHeader) {
        apexRepository.deleteApexItem(apexItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    apexListHeader.numberItem--
                    apexListHeader.totalPrice =
                        (apexListHeader.totalPrice.toLong() - apexItem.price.toLong()).toString()
                    updateApexListHeader(apexListHeader)
                    getApexInvoiceListHeader()
                    getApexItems(apexListHeader)
                }
            })
    }

    fun deleteApexListHeader(apexListHeader: ApexListHeader) {
        apexRepository.deleteApexListHeader(apexListHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApexCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    getApexInvoiceListHeader()
                }
            })
    }

}