package com.wannagohome.viewty.ui.bulletin.article.detail.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.baseclass.BaseViewModel
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
    val reportCommentSuccess = MutableLiveData<Boolean>(false)
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

    fun like(commentId: Int) {
        lensClient.postArticleCommentLike(articleId, commentId)
            .subscribe( { it ->
                val newComment = it.body()
                var commentList = comments.value?.toMutableList()
                if (commentList!=null && newComment !=null){
                    val idx = commentList.indexOfFirst { comment ->  comment.commentId == newComment.commentId }
                    commentList[idx] = newComment
                }
                comments.value = commentList
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike(commentId: Int) {
        lensClient.deleteArticleCommentLike(articleId, commentId)
            .subscribe( {
                val newComment = it.body()
                var commentList = comments.value?.toMutableList()
                if (commentList!=null && newComment !=null){
                    val idx = commentList.indexOfFirst { comment ->  comment.commentId == newComment.commentId }
                    commentList[idx] = newComment
                }
                comments.value = commentList
            }, {
            })
            .addTo(compositeDisposable)
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
    fun reportComment() {
        reportCommentSuccess.value = true
    }
}