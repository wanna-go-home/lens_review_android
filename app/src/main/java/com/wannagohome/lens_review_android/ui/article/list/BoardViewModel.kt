package com.wannagohome.lens_review_android.ui.article.list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.article.ArticlePreview
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class BoardViewModel : BaseViewModel(), KoinComponent {

    val articleList = MutableLiveData<List<ArticlePreview>>()
    val refreshSuccess = MutableLiveData<Boolean>(true)
    private val lensClient: LensApiClient by inject()

    fun getArticleList() {
        lensClient.getArticleList()
            .subscribe(
                {
                    articleList.value = it.body()
                },
                {
                    it.printStackTrace()

                }
            ).addTo(compositeDisposable)

    }


    fun refreshArticleList() {
        refreshSuccess.value = false
        lensClient.getArticleList()
            .subscribe(
                {
                    articleList.value = it.body()
                    refreshSuccess.value = true
                },
                {
                    it.printStackTrace()
                }
            ).addTo(compositeDisposable)
    }
}