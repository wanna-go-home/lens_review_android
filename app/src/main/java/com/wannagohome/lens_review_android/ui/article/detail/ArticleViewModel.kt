package com.wannagohome.lens_review_android.ui.article.detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.article.Comment
import com.wannagohome.lens_review_android.network.model.article.Article
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ArticleViewModel(private val articleId: Int) : BaseViewModel(), KoinComponent {

    val article = MutableLiveData<Article>()
    val comments = MutableLiveData<List<Comment>>()
    val deleteSuccess = MutableLiveData<Boolean>(false)
    val postCommentSuccess = MutableLiveData<Boolean>(false)
    val modifyCommentSuccess = MutableLiveData<Boolean>(false)
    val deleteCommentSuccess = MutableLiveData<Boolean>(false)
    val refreshSuccess = MutableLiveData<Boolean>(true)


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

    fun deleteArticle() {
        lensClient.deleteArticleById(articleId)
            .subscribe( {
                deleteSuccess.value = true
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun refreshArticle() {
        refreshSuccess.value = false
        Observable.zip(
            lensClient.getArticleById(articleId).map { article.value = it.body(); it.isSuccessful },
            lensClient.getCommentsByArticleId(articleId).map { comments.value = it.body(); it.isSuccessful },
            { articleLoaded: Boolean, commentLoaded: Boolean ->articleLoaded && commentLoaded})
            .onErrorReturn { false }
            .subscribe { refreshed ->
                if (refreshed) {
                    refreshSuccess.value = true
                }
            }
    }

    fun getComments() {
        compositeDisposable.add(lensClient.getCommentsByArticleId(articleId).subscribe({
            comments.value = it.body()
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }

    fun postComment(contents: String) {
        compositeDisposable.add(lensClient.writeComment(articleId, contents).subscribe({
            refreshArticle()
            postCommentSuccess.value = true
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
    fun modifyComment(commentId: Int, contents: String) {
        compositeDisposable.add(lensClient.modifyComment(articleId, commentId, contents).subscribe({
            refreshArticle()
            modifyCommentSuccess.value = true
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
    fun deleteComment(commentId: Int) {
        compositeDisposable.add(lensClient.deleteCommentById(articleId, commentId)
            .subscribe({
            refreshArticle()
            deleteCommentSuccess.value = true
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
}