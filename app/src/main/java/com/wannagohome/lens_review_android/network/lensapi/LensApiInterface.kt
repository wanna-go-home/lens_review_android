package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.Lens
import io.reactivex.Observable
import retrofit2.http.GET

interface LensApiInterface {

    @GET("api/lensinfo")
    fun getLensList(): Observable<List<Lens>>

    @GET("api/articleinfo")
    fun getArticleList(): Observable<List<Article>>
}
