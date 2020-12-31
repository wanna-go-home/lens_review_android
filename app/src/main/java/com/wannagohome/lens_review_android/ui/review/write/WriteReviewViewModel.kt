package com.wannagohome.lens_review_android.ui.review.write

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.support.Utils
import org.koin.core.inject
import timber.log.Timber

class WriteReviewViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val writeSuccess = MutableLiveData<Boolean>(false)
    val errMessageLiveData = MutableLiveData<String>()

    val selectedLensLiveData = MutableLiveData<LensPreview>()

    var lensList = listOf<LensPreview>()
    val lensListLiveData = MutableLiveData<List<LensPreview>>()

    var selectedLensId = -1
    var previousSelectedId = -1

    enum class WriteReviewStage{
        SELECT_LENS,
        WRITE_REVIEW,
        OFF,
    }
    val curStageLiveData = MutableLiveData<WriteReviewStage>(WriteReviewStage.SELECT_LENS)

    fun writeReview(title: String, contents: String, lensId: Int) {
        lensApiClient.writeReview(title, contents, lensId)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

    fun writeReview(title:String, contents:String){
        if(title.isEmpty()){
            errMessageLiveData.value = Utils.getString(R.string.write_review_empty_title)
            return
        }

        if(contents.isEmpty()){
            errMessageLiveData.value = Utils.getString(R.string.write_review_empty_contents)
            return
        }

        writeReview(title,contents,selectedLensId)
    }
    fun resetStage(){
        Timber.d("kgp before reset " + curStageLiveData.value!!.ordinal + " " + curStageLiveData.hashCode())

        curStageLiveData.value = WriteReviewStage.SELECT_LENS
        Timber.d("kgp after reset " + curStageLiveData.value!!.ordinal + " " + curStageLiveData.hashCode())

    }
    fun next(){
        Timber.d("kgp before newxt " + curStageLiveData.value!!.ordinal  + " " + curStageLiveData.hashCode())

        curStageLiveData.value = WriteReviewStage.WRITE_REVIEW
        Timber.d("kgp after newxt " + curStageLiveData.value!!.ordinal  + " " + curStageLiveData.hashCode())
    }

    fun back(){
        Timber.d("kgp before back " + curStageLiveData.value!!.ordinal  + " " + curStageLiveData.hashCode())

        curStageLiveData.value = when(curStageLiveData.value){
            WriteReviewStage.SELECT_LENS -> WriteReviewStage.OFF
            WriteReviewStage.WRITE_REVIEW -> WriteReviewStage.SELECT_LENS
            else -> WriteReviewStage.OFF
        }
        Timber.d("kgp before back " + curStageLiveData.value!!.ordinal + " " + curStageLiveData.hashCode())
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

        selectedLensLiveData.value = lensList.first { it.lensId == selectedLensId }
    }

    fun searchLens(name : String){
        if(name.isEmpty()) {
            lensListLiveData.value = lensList
            return
        }
        lensListLiveData.value = lensList.filter { it.name.contains(name) }
    }
}