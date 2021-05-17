package com.wannagohome.viewty.ui.search_lens

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.LensPreview
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LensViewModel @Inject constructor() : BaseViewModel() {

    val lensList = MutableLiveData<List<LensPreview>>()

    @Inject
    lateinit var lensClient: LensApiClient

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