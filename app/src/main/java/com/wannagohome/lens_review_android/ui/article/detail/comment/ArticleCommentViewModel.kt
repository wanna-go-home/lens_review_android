package com.wannagohome.lens_review_android.ui.article.detail.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.comment.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ArticleCommentViewModel(private val articleId: Int, private val parentCommentId: Int? = null) : BaseViewModel(), KoinComponent {

    val comments = MutableLiveData<List<Comment>>()
    private val lensClient: LensApiClient by inject()
    val postCommentSuccess = MutableLiveData<Boolean>(false)
    val refreshSuccess = MutableLiveData<Boolean>(true)
    val modifyCommentSuccess = MutableLiveData<Boolean>(false)
    val deleteCommentSuccess = MutableLiveData<Boolean>(false)
    val finishActivity = MutableLiveData<Boolean>(false)

    fun getCommentsByArticleId() {
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

    fun getCommentsByCommentId() {
        if (parentCommentId != null) {
            compositeDisposable.add(lensClient.getArticleCommentsByCommentId(articleId, parentCommentId).subscribe({
                comments.value = it.body()
            }, {
                //TODO error notification
                if (it is HttpException) {
                    val exception = it
                    Timber.e("HTTP Exception ${exception.response()}")
                }

            }))
        }
    }

    fun refreshComment() {
        refreshSuccess.value = false
        if (parentCommentId != null) {
            compositeDisposable.add(lensClient.getArticleCommentsByCommentId(articleId, parentCommentId).subscribe({
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
    }

    fun postComment(contents: String) {
        compositeDisposable.add(
            lensClient.writeArticleComment(articleId, contents, parentCommentId).subscribe({
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
        compositeDisposable.add(lensClient.modifyArticleComment(articleId, commentId, contents).subscribe({
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
            lensClient.deleteArticleCommentById(articleId, commentId)
                .subscribe({
                    if (commentId == parentCommentId){
                        finishActivity.value = true
                    }
                    else {
                        deleteCommentSuccess.value = true
                    }

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