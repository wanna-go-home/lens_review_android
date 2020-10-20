package com.wannagohome.lens_review_android.ui.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.Article
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BoardViewModel : ViewModel(), KoinComponent {

    val articleList = MutableLiveData<List<Article>>()

    private val disposable = CompositeDisposable()

    private val lensClient: LensApiClient by inject()

    fun getArticleList() {
        val boardListReq = lensClient.getArticleList()
            .subscribe(
                {
                    articleList.value = it.body()
                },
                {
                    it.printStackTrace()

                }
            )

        disposable.add(boardListReq)
    }
}