package com.wannagohome.viewty.ui.mypage.myarticlecomment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MyCommentViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var lensApiClient: LensApiClient

    val myCommentList = MutableLiveData<List<Comment>>()

    fun fetchMyCommentList() {
        lensApiClient.getMyComments()
            .subscribe({
                val myComments = it.body()

                myCommentList.value = myComments!!
            }, {
                Timber.d(it)
            })
            .addTo(compositeDisposable)
    }

}