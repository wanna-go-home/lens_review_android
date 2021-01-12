package com.wannagohome.lens_review_android.ui.article.detail.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.article.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class CommentViewModel(private val articleId: Int, private val parentCommentId: Int) : BaseViewModel(), KoinComponent {

    val comments = MutableLiveData<List<Comment>>()
    private val lensClient: LensApiClient by inject()
    val postCommentSuccess = MutableLiveData<Boolean>(false)
    val refreshSuccess = MutableLiveData<Boolean>(true)
    val modifyCommentSuccess = MutableLiveData<Boolean>(false)
    val deleteCommentSuccess = MutableLiveData<Boolean>(false)

    fun getComments() {
        compositeDisposable.add(lensClient.getCommentsByCommentId(articleId, parentCommentId).subscribe({
            comments.value = it.body()
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }

    fun refreshComment() {
        refreshSuccess.value = false
        compositeDisposable.add(lensClient.getCommentsByCommentId(articleId, parentCommentId).subscribe({
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

    fun postComment(contents: String) {
        compositeDisposable.add(
            lensClient.writeComment(
                articleId,
                contents, parentCommentId
            ).subscribe({
                refreshComment()
                postCommentSuccess.value = true
            }, {
                //TODO error notification
                if (it is HttpException) {
                    val exception = it
                    Timber.e("HTTP Exception ${exception.response()}")
                }

            })
        )
    }

    fun modifyComment(commentId: Int, contents: String) {
        compositeDisposable.add(lensClient.modifyComment(articleId, commentId, contents).subscribe({
            refreshComment()
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
        compositeDisposable.add(
            lensClient.deleteCommentById(articleId, commentId)
                .subscribe({
                    refreshComment()
                    deleteCommentSuccess.value = true
                }, {
                    //TODO error notification
                    if (it is HttpException) {
                        val exception = it
                        Timber.e("HTTP Exception ${exception.response()}")
                    }

                })
        )
    }
}