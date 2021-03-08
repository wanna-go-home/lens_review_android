package com.wannagohome.viewty.ui.mypage.myarticlecomment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.baseclass.BaseViewModel
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