package com.wannagohome.lens_review_android.ui.board.article

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.article.Comment
import com.wannagohome.lens_review_android.network.model.article.Article
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ArticleViewModel : BaseViewModel(), KoinComponent {

    companion object {
        const val ROOT_COMMENT = 0
    }
    val article = MutableLiveData<Article>()
    val comments = MutableLiveData<List<Comment>>()
    val deleteSuccess = MutableLiveData<Boolean>(false)
    val postCommentSuccess = MutableLiveData<Boolean>(false)

    private val lensClient: LensApiClient by inject()

    fun getArticle(articleId: Int) {
        compositeDisposable.add(lensClient.getArticleById(articleId).subscribe({
            Timber.d(it.body()?.title)
            article.value = it.body()
        }, {

            //TODO error notification

            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }

    fun deleteArticle(articleId: Int) {
        lensClient.deleteArticleById(articleId)
            .subscribe( {
                //@todo : 이거왜안대냐
                deleteSuccess.value = true
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun getComments(articleId: Int) {
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

    fun postComment(articleId: Int, contents: String) {
        compositeDisposable.add(lensClient.writeComment(articleId, ROOT_COMMENT, contents).subscribe({
            postCommentSuccess.value = true
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
}