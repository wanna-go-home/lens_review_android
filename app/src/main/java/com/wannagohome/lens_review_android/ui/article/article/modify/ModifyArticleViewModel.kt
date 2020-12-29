package com.wannagohome.lens_review_android.ui.article.article.modify

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.model.Article
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber

class ModifyArticleViewModel : BaseViewModel(), KoinComponent {

    val article = MutableLiveData<Article>()
    private val lensApiClient: LensApiClient by inject()

    fun getArticle(articleId: Int) {
        compositeDisposable.add(lensApiClient.getArticleById(articleId).subscribe({
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

    val writeSuccess = MutableLiveData<Boolean>(false)

    fun modifyArticle(id: Int, title: String, contents: String) {
        lensApiClient.modifyArticle(id, title, contents)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

}