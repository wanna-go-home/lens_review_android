package com.wannagohome.viewty.ui.bulletin.article.list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import com.wannagohome.viewty.extension.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class BoardViewModel : BaseViewModel(), KoinComponent {

    val articleList = MutableLiveData<List<Article>>()
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

    fun like(articleId: Int) {
        lensClient.postArticleLike(articleId)
            .subscribe( {
                val newArticle = it.body()
                var oldArticleList = articleList.value?.toMutableList()
                if (oldArticleList!=null && newArticle !=null){
                    val idx = oldArticleList.indexOfFirst { article ->  article.articleId == newArticle.articleId }
                    oldArticleList[idx] = newArticle
                }
                articleList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike(articleId: Int) {
        lensClient.deleteArticleLike(articleId)
            .subscribe( {
                val newArticle = it.body()
                var oldArticleList = articleList.value?.toMutableList()
                if (oldArticleList!=null && newArticle !=null){
                    val idx = oldArticleList.indexOfFirst { article ->  article.articleId == newArticle.articleId }
                    oldArticleList[idx] = newArticle
                }
                articleList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }
}