package com.wannagohome.viewty.ui.lens_detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class LensDetailViewModel : BaseViewModel() {

//    val detailedLens = MutableLiveData<DetailedLens>()

    val lensName = MutableLiveData<String>()

    val lensPrice = MutableLiveData<String>()

    val lensGraphicDia = MutableLiveData<String>()

    val productImage = MutableLiveData<String>()

    @Inject
    lateinit var lensClient: LensApiClient

    fun getLensDetail(lensId: Int) {
        compositeDisposable.add(lensClient.getLensById(lensId).subscribe({
            Timber.d(it.body()?.name)
//            detailedLens.value = it.body()
            val lensDetail = it.body()

            lensDetail?.let { lens ->
                lensName.value = lens.name

                lensPrice.value = lens.price.toString()

                lensGraphicDia.value = lens.graphicDia.toString()

                if (lens.productImage.isNotEmpty())
                    productImage.value = lens.productImage[0]
            }

        }, {

            //TODO error notification

            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
}