package com.wannagohome.viewty.support.baseclass

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable


open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}