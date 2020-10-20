package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.DetailedArticle
import com.wannagohome.lens_review_android.network.model.DetailedLens
import com.wannagohome.lens_review_android.network.model.Lens
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LensApiInterface {

    @GET("api/lensinfo")
    fun getLensList(): Observable<Response<List<Lens>>>

    @GET("api/lensinfo")
    fun getLensById(@Query("id") lensId: Int): Observable<Response<DetailedLens>>

    @GET("/api/free-board/preview")
    fun getArticleList(): Observable<Response<List<Article>>>
    @GET("api/articleinfo")
    fun getArticleById(@Query("id") articleId: Int): Observable<Response<DetailedArticle>>
}
