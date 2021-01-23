package com.wannagohome.lens_review_android.ui.mypage.myarticlecomment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.comment.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.inject
import timber.log.Timber

class MyCommentViewModel : BaseViewModel() {

    private val lensApiClient: LensApiClient by inject()

    val myCommentList = MutableLiveData<List<Comment>>()

    fun fetchMyCommentList() {
        lensApiClient.getMyComments()
            .subscribe({
                val myComments = it.body()

                myCommentList.value = myComments
            }, {
                Timber.d(it)
            })
            .addTo(compositeDisposable)
    }

}