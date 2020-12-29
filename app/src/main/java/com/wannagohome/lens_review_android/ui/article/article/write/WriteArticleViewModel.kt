package com.wannagohome.lens_review_android.ui.article.article.write

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import org.koin.core.inject

class WriteArticleViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val writeSuccess = MutableLiveData<Boolean>(false)

    fun writeArticle(title: String, contents: String) {
        lensApiClient.writeArticle(title, contents)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

}