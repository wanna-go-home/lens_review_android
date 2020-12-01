package com.wannagohome.lens_review_android.ui.board.article.comment

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class CommentViewModel : BaseViewModel(), KoinComponent {

    val comments = MutableLiveData<List<Comment>>()
    private val lensClient: LensApiClient by inject()

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
}