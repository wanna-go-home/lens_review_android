package com.wannagohome.viewty.ui.bulletin.article.write

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteArticleViewModel @Inject constructor() : BaseViewModel() {

    private val articleId: Int = -1

    @Inject
    lateinit var lensApiClient: LensApiClient
    val article = MutableLiveData<Article>()
    val writeSuccess = MutableLiveData<Boolean>(false)

    fun getArticle() {
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

    fun writeArticle(title: String, contents: String) {
        lensApiClient.writeArticle(title, contents)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

    fun modifyArticle(title: String, contents: String) {
        lensApiClient.modifyArticle(articleId, title, contents)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

}