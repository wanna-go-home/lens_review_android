package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.DetailedArticle
import com.wannagohome.lens_review_android.network.model.DetailedLens
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.SignUpRequest
import io.reactivex.rxjava3.core.Observable
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
    fun login(@Body loginRequest: LoginRequest): Observable<Response<ResponseBody>>

    @POST("api/user/signup")
    fun signUp(@Body signUpRequestRequest: SignUpRequest): Observable<Response<ResponseBody>>
}
