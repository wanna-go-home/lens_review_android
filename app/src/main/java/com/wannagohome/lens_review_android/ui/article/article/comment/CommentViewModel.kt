package com.wannagohome.lens_review_android.ui.article.article.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.article.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.ui.board.article.ArticleViewModel
import io.reactivex.rxjava3.core.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class CommentViewModel : BaseViewModel(), KoinComponent {
    val comments = MutableLiveData<List<Comment>>()
    private val lensClient: LensApiClient by inject()
    val postCommentSuccess = MutableLiveData<Boolean>(false)
    val refreshSuccess = MutableLiveData<Boolean>(false)

    fun getComments(articleId: Int, commentId: Int) {
        compositeDisposable.add(lensClient.getCommentsByCommentId(articleId, commentId).subscribe({
            comments.value = it.body()
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
    fun refreshComment(articleId: Int, commentId: Int) {
        refreshSuccess.value = false
        compositeDisposable.add(lensClient.getCommentsByCommentId(articleId, commentId).subscribe({
            comments.value = it.body()
            refreshSuccess.value = true
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }
    fun postComment(articleId: Int, parentId: Int, contents: String) {
        compositeDisposable.add(lensClient.writeComment(articleId,
            contents, parentId).subscribe({
            refreshComment(articleId,parentId)
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