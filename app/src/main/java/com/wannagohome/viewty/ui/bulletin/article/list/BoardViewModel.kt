package com.wannagohome.viewty.ui.bulletin.article.list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BoardViewModel @Inject constructor() : BaseViewModel() {

    val articleList = MutableLiveData<List<Article>>()
    val refreshSuccess = MutableLiveData<Boolean>(true)

    @Inject
    lateinit var lensClient: LensApiClient

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
            .subscribe({
                val newArticle = it.body()
                var oldArticleList = articleList.value?.toMutableList()
                if (oldArticleList != null && newArticle != null) {
                    val idx = oldArticleList.indexOfFirst { article -> article.articleId == newArticle.articleId }
                    oldArticleList[idx] = newArticle
                }
                articleList.value = oldArticleList!!
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike(articleId: Int) {
        lensClient.deleteArticleLike(articleId)
            .subscribe({
                val newArticle = it.body()
                var oldArticleList = articleList.value?.toMutableList()
                if (oldArticleList != null && newArticle != null) {
                    val idx = oldArticleList.indexOfFirst { article -> article.articleId == newArticle.articleId }
                    oldArticleList[idx] = newArticle
                }
                articleList.value = oldArticleList!!
            }, {
            })
            .addTo(compositeDisposable)
    }
}