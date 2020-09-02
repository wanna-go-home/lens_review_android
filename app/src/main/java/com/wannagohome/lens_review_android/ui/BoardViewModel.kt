package com.wannagohome.lens_review_android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.Article
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BoardViewModel : ViewModel(), KoinComponent {

    val articleList = MutableLiveData<List<Article>>()

//    private val disposable = CompositeDisposable()
//
//    private val lensClient: LensApiClient by inject()

    fun getArticleList() {
        //For Test
        val item1 = Article(1,1,"Title1","test Text",13,8,4,"202008311838","http://nouri.com:8080/article:51")
        val item2 = Article(2,2,"Title2","test Text",17,15,6,"202008311840","http://nouri.com:8080/article:52")
        val item3 = Article(3,3,"Title3","test Text",1,1,7,"202008311864","http://nouri.com:8080/article:53")
        val item4 = Article(4,4,"Title4","test Text",353,14,5,"202008311879","http://nouri.com:8080/article:54")
        val items = mutableListOf<Article>(item1, item2, item3, item4)
        articleList.value =items
//        val boardListReq = lensClient.getBoardList()
//            .subscribe(
//                {
//                    articleList.value = it
//                },
//                {
//                    it.printStackTrace()
//
//                }
//            )
//        disposable.add(boardListReq)
    }
}