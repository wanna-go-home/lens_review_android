package com.wannagohome.viewty.ui.mypage.myarticle

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import org.koin.core.inject

class MyArticleViewModel : BaseViewModel() {

    val myArticleList = MutableLiveData<List<Article>>()

    val lensApiClient: LensApiClient by inject()

    fun getMyArticle() {
        lensApiClient.getMyArticle()
            .subscribe({
                val articleList = it.body()!!

                myArticleList.value = articleList

            }, {})
    }
}