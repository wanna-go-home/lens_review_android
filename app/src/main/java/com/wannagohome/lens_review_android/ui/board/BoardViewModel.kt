package com.wannagohome.lens_review_android.ui.board

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.support.disposableExt.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class BoardViewModel : BaseViewModel(), KoinComponent {

    val articleList = MutableLiveData<List<Article>>()
    private val lensClient: LensApiClient by inject()

    fun getArticleList() {
        val boardListReq = lensClient.getArticleList()
            .subscribe(
                {
                    articleList.value = it.body()
                },
                {
                    it.printStackTrace()

                }
            ).addTo(compositeDisposable)

    }
}