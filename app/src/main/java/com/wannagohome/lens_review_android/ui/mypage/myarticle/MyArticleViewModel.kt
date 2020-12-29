package com.wannagohome.lens_review_android.ui.mypage.myarticle

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.ArticlePreview
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.inject

class MyArticleViewModel : BaseViewModel() {

    val myArticleList = MutableLiveData<List<ArticlePreview>>()

    val lensApiClient: LensApiClient by inject()

    fun getMyArticle() {
        lensApiClient.getMyArticle()
            .subscribe({
                val articleList = it.body()!!

                myArticleList.value = articleList

            }, {})
    }
}