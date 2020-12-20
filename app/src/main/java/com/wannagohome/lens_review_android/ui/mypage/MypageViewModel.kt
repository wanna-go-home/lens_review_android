package com.wannagohome.lens_review_android.ui.mypage

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.inject
import timber.log.Timber

class MypageViewModel : BaseViewModel() {

    private val lensApiClient: LensApiClient by inject()

    val myReviewCount = MutableLiveData<Int>()

    val myArticleCount = MutableLiveData<Int>()

    val myCommentCount = MutableLiveData<Int>()

    val myNickname = MutableLiveData<String>()

    init{
        fetchMyInfo()
    }

    fun fetchMyInfo() {
        lensApiClient.myInfo()
            .subscribe({
                val myInfo = it.body()!!

                myReviewCount.value = myInfo.reviewCount

                myArticleCount.value = myInfo.articleCount

                myCommentCount.value = myInfo.articleCommentCount + myInfo.reviewCommentCount

                myNickname.value = myInfo.nickname

            }, {
                Timber.e(it)
            })
            .addTo(compositeDisposable)
    }
}