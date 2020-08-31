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
        var item1 = Article(1,1,"Sample1","test Text",13,8,4,"202008311838","http://nouri.com:8080/article:51")
        var item2 = Article(2,2,"Sample2","test Text",17,15,6,"202008311840","http://nouri.com:8080/article:52")
        var item3 = Article(3,3,"Sample3","test Text",1,1,7,"202008311864","http://nouri.com:8080/article:53")
        var item4 = Article(4,4,"Sample4","test Text",353,14,5,"202008311879","http://nouri.com:8080/article:54")
        var items = mutableListOf<Article>(item1, item2, item3, item4)
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