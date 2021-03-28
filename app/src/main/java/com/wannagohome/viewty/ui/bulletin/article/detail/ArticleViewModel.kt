package com.wannagohome.viewty.ui.bulletin.article.detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ArticleViewModel(private val articleId: Int) : BaseViewModel(), KoinComponent {

    val article = MutableLiveData<Article>()
    val deleteSuccess = MutableLiveData<Boolean>(false)
    val reportSuccess = MutableLiveData<Boolean>(false)

    private val lensClient: LensApiClient by inject()

    fun getArticle() {
        compositeDisposable.add(lensClient.getArticleById(articleId).subscribe({
            article.value = it.body()
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }

    fun like() {
        lensClient.postArticleLike(articleId)
            .subscribe( {
                article.value = it.body()
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike() {
        lensClient.deleteArticleLike(articleId)
            .subscribe( {
                article.value = it.body()
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun deleteArticle() {
        lensClient.deleteArticleById(articleId)
            .subscribe( {
                deleteSuccess.value = true
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun reportArticle() {
        reportSuccess.value = true
    }
}