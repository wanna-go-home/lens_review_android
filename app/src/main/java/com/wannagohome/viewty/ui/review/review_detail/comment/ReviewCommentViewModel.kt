package com.wannagohome.viewty.ui.review.review_detail.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ReviewCommentViewModel(private val reviewId: Int, private val parentCommentId: Int? = null) : BaseViewModel(), KoinComponent {

    val comments = MutableLiveData<List<Comment>>()
    private val lensClient: LensApiClient by inject()
    val postCommentSuccess = MutableLiveData<Boolean>(false)
    val refreshSuccess = MutableLiveData<Boolean>(true)
    val modifyCommentSuccess = MutableLiveData<Boolean>(false)
    val deleteCommentSuccess = MutableLiveData<Boolean>(false)
    val reportCommentSuccess = MutableLiveData<Boolean>(false)
    val finishActivity = MutableLiveData<Boolean>(false)

    fun getCommentsByReviewId() {
        compositeDisposable.add(lensClient.getCommentsByReviewId(reviewId).subscribe({
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
            compositeDisposable.add(lensClient.getReviewCommentsByCommentId(reviewId, parentCommentId).subscribe({
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
        lensClient.postReviewCommentLike(reviewId, commentId)
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

    fun unlike(commentId: Int) {
        lensClient.deleteReviewCommentLike(reviewId, commentId)
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
            compositeDisposable.add(lensClient.getReviewCommentsByCommentId(reviewId, parentCommentId).subscribe({
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
            lensClient.writeReviewComment(reviewId, contents, parentCommentId).subscribe({
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
        compositeDisposable.add(lensClient.modifyReviewComment(reviewId, commentId, contents).subscribe({
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
            lensClient.deleteReviewCommentById(reviewId, commentId)
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

    fun reportComment(){
        reportCommentSuccess.value = true
    }
}