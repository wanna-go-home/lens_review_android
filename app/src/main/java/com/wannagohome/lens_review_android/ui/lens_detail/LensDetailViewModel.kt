package com.wannagohome.lens_review_android.ui.lens_detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.DetailedLens
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class LensDetailViewModel : BaseViewModel(), KoinComponent {

//    val detailedLens = MutableLiveData<DetailedLens>()

    val lensName = MutableLiveData<String>()

    val lensPrice = MutableLiveData<String>()

    val lensGraphicDia = MutableLiveData<String>()

    val productImage = MutableLiveData<String>()

    private val lensClient: LensApiClient by inject()

    fun getLensDetail(lensId: Int) {
        disposable.add(lensClient.getLensById(lensId).subscribe({
            Timber.d(it.body()?.name)
//            detailedLens.value = it.body()
            val lensDetail = it.body()

            lensDetail?.let{lens->
                lensName.value = lens.name

                lensPrice.value = lens.price.toString()

                lensGraphicDia.value = lens.graphicDia.toString()

                if(lens.productImage.isNotEmpty())
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