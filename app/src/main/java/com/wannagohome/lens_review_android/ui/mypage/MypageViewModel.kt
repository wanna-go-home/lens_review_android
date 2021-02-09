package com.wannagohome.lens_review_android.ui.mypage

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.AccessKeyHelper
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.inject
import timber.log.Timber

class MypageViewModel : BaseViewModel() {

    private val lensApiClient: LensApiClient by inject()

    val myReviewCount = MutableLiveData<Int>()

    val myArticleCount = MutableLiveData<Int>()

    val myCommentCount = MutableLiveData<Int>()

    val myNickname = MutableLiveData<String>()

    val successLeave = MutableLiveData<Boolean>()

    val successLogout = MutableLiveData<Boolean>()

    val successModifyNickname = MutableLiveData<Boolean>()

    init {
        fetchMyInfo()
    }

    fun logout() {
        AccessKeyHelper.deleteTokenSync()

        successLogout.value = true
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

    fun leave() {
        lensApiClient.leave()
            .subscribe({
                successLeave.value = true
            }, {

            }).addTo(compositeDisposable)
    }

    fun modifyNickname(nickname: String) {
        if (nickname.isEmpty()) {

            return
        }

        lensApiClient.modifyNickname(nickname)
            .doOnNext {

                successModifyNickname.value = true
            }
            .subscribe {
                fetchMyInfo()
            }
            .addTo(compositeDisposable)

    }
}