package com.wannagohome.lens_review_android.ui.review.write

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.model.LensPreview
import org.koin.core.inject
import timber.log.Timber

class WriteReviewViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val writeSuccess = MutableLiveData<Boolean>(false)


    val selectedLensLiveData = MutableLiveData<LensPreview>()
    val previousSelectedLensLiveData = MutableLiveData<LensPreview>()

    var lensList = listOf<LensPreview>()
    val lensListLiveData = MutableLiveData<List<LensPreview>>()

    var selectedLensId = -1
    var previousSelectedId = -1

    fun writeReview(title: String, contents: String, lensId: Int) {
        lensApiClient.writeReview(title, contents, lensId)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

    fun getLensList(){
        lensApiClient.getLensList()
            .subscribe ({

                lensList = it.body()!!

                lensListLiveData.value = lensList

                if(selectedLensId == -1)
                    selectLens(1)
            },{})
            .addTo(compositeDisposable)
    }


    fun selectLens(selectLensId : Int){
        previousSelectedId = selectedLensId

        selectedLensId = selectLensId

        previousSelectedLensLiveData.value = lensList.first {it.lensId == previousSelectedId }
        selectedLensLiveData.value = lensList.first { it.lensId == selectedLensId }

    }

}