package com.wannagohome.lens_review_android.support.basemodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}