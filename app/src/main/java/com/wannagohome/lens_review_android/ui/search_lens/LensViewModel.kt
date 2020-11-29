package com.wannagohome.lens_review_android.ui.search_lens

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class LensViewModel : BaseViewModel(), KoinComponent {

    val lensList = MutableLiveData<List<LensPreview>>()

    private val lensClient: LensApiClient by inject()

    fun getLensList() {
        lensClient.getLensList()
            .subscribe(
                {
                    lensList.value = it.body()
                },
                {
                    it.printStackTrace()

                }
            ).addTo(compositeDisposable)
    }
}