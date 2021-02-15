package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.*
import com.wannagohome.lens_review_android.network.model.article.*
import com.wannagohome.lens_review_android.network.model.comment.Comment
import com.wannagohome.lens_review_android.network.model.comment.WriteCommentRequest
import com.wannagohome.lens_review_android.network.model.review.*
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.ModifyNicknameRequest
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

    @GET("api/boards/article")
    fun getArticleList(): Observable<Response<List<Article>>>

    @GET("api/boards/article/{id}")
    fun getArticleById(@Path("id") articleId: Int): Observable<Response<Article>>

    @DELETE("api/boards/article/{id}")
    fun deleteArticleById(@Path("id") articleId: Int): Observable<Response<ResponseBody>>

    @POST("api/boards/article")
    fun writeArticle(@Body writeArticleRequest: WriteArticleRequest): Observable<Response<ResponseBody>>

    @PUT("api/boards/article/{id}")
    fun modifyArticle(@Path("id") articleId: Int, @Body writeArticleRequest: WriteArticleRequest): Observable<Response<ResponseBody>>

    @GET("/api/boards/article/{id}/comments")
    fun getCommentsByArticleId(@Path("id") articleId: Int): Observable<Response<List<Comment>>>

    @GET("/api/boards/article/{articleId}/comments/{commentId}")
    fun getArticleCommentsByCommentId(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int): Observable<Response<List<Comment>>>

    @POST("api/boards/article/{articleId}/comments")
    fun writeArticleComment(@Path("articleId") articleId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>

    @PUT("api/boards/article/{articleId}/comments/{commentId}")
    fun modifyArticleComment(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>

    @DELETE("api/boards/article/{articleId}/comments/{commentId}")
    fun deleteArticleCommentById(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int ): Observable<Response<ResponseBody>>

    @POST("api/user/login")
    fun login(@Body loginRequest: LoginRequest): Observable<Response<ResponseBody>>

    @GET("api/user/check/id")
    fun checkSameId(@Query("id") emailId: String): Observable<Response<ResponseBody>>

    @GET("api/user/check/nickname")
    fun checkSameNickname(@Query("nickname") nickname: String): Observable<Response<ResponseBody>>

    @POST("api/user/signup")
    fun signUp(@Body signUpRequestRequest: SignUpRequest): Observable<Response<ResponseBody>>

    @GET("api/user/me")
    fun me(): Observable<Response<MyInfo>>

    @DELETE("api/user")
    fun leave(): Observable<Response<ResponseBody>>

    @GET("api/boards/review-board")
    fun getAllReviews(): Observable<Response<List<Review>>>

    @GET("api/boards/review-board/{id}")
    fun getReviewById(@Path("id") reviewId: Int): Observable<Response<Review>>

    @DELETE("api/boards/review-board/{id}")
    fun deleteReviewById(@Path("id") reviewId: Int): Observable<Response<ResponseBody>>

    @GET("/api/boards/review-board/{id}/comments")
    fun getCommentsByReviewId(@Path("id") reviewId: Int): Observable<Response<List<Comment>>>

    @GET("/api/boards/review-board/{reviewId}/comments/{commentId}")
    fun getReviewCommentsByCommentId(@Path("reviewId") reviewId: Int, @Path("commentId") commentId: Int): Observable<Response<List<Comment>>>

    @POST("api/boards/review-board")
    fun writeReview(@Body writeReviewRequest: WriteReviewRequest): Observable<Response<ResponseBody>>

    @POST("api/boards/review-board/{reviewId}/comments")
    fun writeReviewComment(@Path("reviewId") reviewId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>

    @PUT("api/boards/review-board/{reviewId}/comments/{commentId}")
    fun modifyReviewComment(@Path("reviewId") reviewId: Int, @Path("commentId") commentId: Int, @Body writeCommentRequest: WriteCommentRequest): Observable<Response<ResponseBody>>

    @DELETE("api/boards/review-board/{reviewId}/comments/{commentId}")
    fun deleteReviewCommentById(@Path("reviewId") reviewId: Int, @Path("commentId") commentId: Int ): Observable<Response<ResponseBody>>

    @GET("api/user/article/me")
    fun getMyArticle(): Observable<Response<List<Article>>>

    @GET("api/user/review/me")
    fun getMyReview(): Observable<Response<List<Review>>>

    @GET("api/user/comments/me")
    fun getMyComments(): Observable<Response<List<Comment>>>

    @PUT("api/user/modify/nickname")
    fun modifyNickname(@Body nickname: ModifyNicknameRequest): Observable<Response<ResponseBody>>

    @POST("/api/boards/article/{articleId}/comments/{commentId}/like")
    fun postArticleCommentLike(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int): Observable<Response<Comment>>

    @DELETE("/api/boards/article/{articleId}/comments/{commentId}/like")
    fun deleteArticleCommentLike(@Path("articleId") articleId: Int, @Path("commentId") commentId: Int): Observable<Response<Comment>>

    @POST("/api/boards/article/{articleId}/like")
    fun postArticleLike(@Path("articleId") articleId: Int): Observable<Response<Article>>

    @DELETE("/api/boards/article/{articleId}/like")
    fun deleteArticleLike(@Path("articleId") articleId: Int): Observable<Response<Article>>

    @POST("/api/boards/review-board/{reviewId}/comments/{commentId}/like")
    fun postReviewCommentLike(@Path("reviewId") reviewId: Int, @Path("commentId") commentId: Int): Observable<Response<Comment>>

    @DELETE("/api/boards/review-board/{reviewId}/comments/{commentId}/like")
    fun deleteReviewCommentLike(@Path("reviewId") reviewId: Int, @Path("commentId") commentId: Int): Observable<Response<Comment>>

    @POST("/api/boards/review-board/{reviewId}/like")
    fun postReviewLike(@Path("reviewId") reviewId: Int): Observable<Response<Review>>

    @DELETE("/api/boards/review-board/{reviewId}/like")
    fun deleteReviewLike(@Path("reviewId") reviewId: Int): Observable<Response<Review>>

}
