package com.wannagohome.lens_review_android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.Lens
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class LensViewModel : ViewModel(), KoinComponent {

    val lensList = MutableLiveData<List<Lens>>()

    private val disposable = CompositeDisposable()

    private val lensClient: LensApiClient by inject()

    fun getLensList() {
        val lensListReq = lensClient.getLensList()
            .subscribe(
                {
                    lensList.value = it
                },
                {
                    it.printStackTrace()

                }
            )

        disposable.add(lensListReq)
    }
}