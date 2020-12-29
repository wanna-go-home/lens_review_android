package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.*
import com.wannagohome.lens_review_android.network.model.article.*
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.network.model.review.WriteReviewRequest
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.MyInfo
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
    fun getArticleList(): Observable<Response<List<ArticlePreview>>>

    @GET("api/boards/free-board/{id}")
    fun getArticleById(@Path("id") articleId: Int): Observable<Response<Article>>

    @DELETE("api/boards/free-board/{id}")
    fun deleteArticleById(@Path("id") articleId: Int): Observable<Response<ResponseBody>>

    @POST("api/boards/free-board")
    fun writeArticle(@Body writeArticleRequest: WriteArticleRequest): Observable<Response<ResponseBody>>

    @PUT("api/boards/free-board/{id}")
    fun modifyArticle(@Path("id") articleId: Int, @Body writeArticleRequest: WriteArticleRequest): Observable<Response<ResponseBody>>

    @GET("api/boards/free-board/{id}/comments")
    fun getCommentsByArticleId(@Path("id") articleId: Int): Observable<Response<List<Comment>>>

    @GET("api/boards/free-board/{articleId}/comment/{commentId}")
    fun getCommentsByCommentId(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int): Observable<Response<List<Comment>>>

    @POST("api/boards/free-board/{id}/comments")
    fun writeComment(@Path("articleId") articleId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>

    @PUT("api/boards/free-board/{id}/comments/{commentId}")
    fun modifyComment(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>


    @POST("api/user/login")
    fun login(@Body loginRequest: LoginRequest): Observable<Response<ResponseBody>>

    @GET("api/user/check/id")
    fun checkSameId(@Query("id") emailId: String): Observable<Response<ResponseBody>>

    @GET("api/user/check/nickname")
    fun checkSameNickname(@Query("nickname") nickname: String): Observable<Response<ResponseBody>>

    @POST("api/user/signup")
    fun signUp(@Body signUpRequestRequest: SignUpRequest): Observable<Response<ResponseBody>>

    @GET("api/user/me")
    fun me() : Observable<Response<MyInfo>>

    @GET("api/boards/review-board")
    fun getAllReviews(): Observable<Response<List<ReviewPreview>>>

    @POST("api/boards/review-board")
    fun writeReview(@Body writeReviewRequest: WriteReviewRequest): Observable<Response<ResponseBody>>

//    @GET("api/boards/review-board/{id}")
//    fun getReviewById(@Path("id") id : Int) : Observable<Response<DetailedReview>>
}
