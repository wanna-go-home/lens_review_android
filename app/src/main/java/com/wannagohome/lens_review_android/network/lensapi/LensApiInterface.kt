package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.*
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.LoginResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LensApiInterface {

    @GET("api/lens")
    fun getLensList(): Observable<Response<List<LensPreview>>>

    @GET("api/lens/{id}")
    fun getLensById(@Path("id") lensId: Int): Observable<Response<DetailedLens>>

    @GET("api/boards/free-board")
    fun getArticleList(): Observable<Response<List<Article>>>

    @GET("api/boards/free-board/{id}")
    fun getArticleById(@Path("id") articleId: Int): Observable<Response<DetailedArticle>>

    @POST("api/user/login")
    fun login(@Header("account") account: String, @Header("pw") pw: String): Observable<Response<ResponseBody>>
}
