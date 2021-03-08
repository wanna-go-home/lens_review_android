package com.wannagohome.viewty.ui.search_lens

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.LensPreview
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import com.wannagohome.viewty.extension.addTo
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