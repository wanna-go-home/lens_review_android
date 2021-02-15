package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.*
import com.wannagohome.lens_review_android.network.model.article.*
import com.wannagohome.lens_review_android.network.model.comment.Comment
import com.wannagohome.lens_review_android.network.model.comment.WriteCommentRequest
import com.wannagohome.lens_review_android.network.model.review.Review
import com.wannagohome.lens_review_android.network.model.review.WriteReviewRequest
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.ModifyNicknameRequest
import com.wannagohome.lens_review_android.network.model.user.MyInfo
import com.wannagohome.lens_review_android.network.model.user.SignUpRequest
import com.wannagohome.lens_review_android.support.AccessKeyHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response


class LensApiClient(private val lensApiInterface: LensApiInterface) {

    fun getLensList(): Observable<Response<List<LensPreview>>> {
        return lensApiInterface.getLensList()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLensById(lensId: Int): Observable<Response<DetailedLens>> {
        return lensApiInterface.getLensById(lensId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticleList(): Observable<Response<List<Article>>> {
        return lensApiInterface.getArticleList()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticleById(articleId: Int): Observable<Response<Article>> {
        return lensApiInterface.getArticleById(articleId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteArticleById(articleId: Int): Observable<Response<ResponseBody>> {
        return lensApiInterface.deleteArticleById(articleId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun writeArticle(title: String, content: String): Observable<Response<ResponseBody>> {
        val writeArticleRequest = WriteArticleRequest(title, content)

        return lensApiInterface.writeArticle(writeArticleRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun modifyArticle(id: Int, title: String, content: String): Observable<Response<ResponseBody>> {

        val writeArticleRequest = WriteArticleRequest(title, content)

        return lensApiInterface.modifyArticle(id, writeArticleRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCommentsByArticleId(articleId: Int): Observable<Response<List<Comment>>> {
        return lensApiInterface.getCommentsByArticleId(articleId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticleCommentsByCommentId(articleId: Int, commentId: Int): Observable<Response<List<Comment>>> {
        return lensApiInterface.getArticleCommentsByCommentId(articleId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun writeArticleComment(articleId: Int, contents: String, bundleId: Int?=null): Observable<Response<ResponseBody>> {
        val writeCommentRequest = WriteCommentRequest(contents, bundleId)

        return lensApiInterface.writeArticleComment(articleId, writeCommentRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun modifyArticleComment(articleId: Int, commentId: Int, contents: String): Observable<Response<ResponseBody>> {
        val writeCommentRequest = WriteCommentRequest(contents, null)

        return lensApiInterface.modifyArticleComment(articleId, commentId, writeCommentRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteArticleCommentById(articleId: Int, commentId: Int): Observable<Response<ResponseBody>> {

        return lensApiInterface.deleteArticleCommentById(articleId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(account: String, pw: String): Observable<Response<ResponseBody>> {
        val loginRequest = LoginRequest(account, pw)

        return lensApiInterface.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .map { t ->
                if (t.isSuccessful) {
                    val token = t.headers()["Authorization"]
                    if (token != null && token.isNotEmpty() && token.isNotBlank()) {

                        AccessKeyHelper.addToken(token)

                        return@map t
                    }
                    //TODO token 미포함 에러처리
                    else throw HttpException(t)
                } else throw HttpException(t)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun leave(): Observable<Response<ResponseBody>> {
        return lensApiInterface.leave()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun checkSameId(email: String): Observable<Response<ResponseBody>> {
        return lensApiInterface.checkSameId(email)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun checkSameNickname(nickname: String): Observable<Response<ResponseBody>> {
        return lensApiInterface.checkSameNickname(nickname)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun myInfo(): Observable<Response<MyInfo>> {
        return lensApiInterface.me()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun signUp(email: String, pw: String, phoneNumber: String, nickname: String): Observable<Response<ResponseBody>> {
        val signUpRequest = SignUpRequest(email, pw, phoneNumber, nickname)
        return lensApiInterface.signUp(signUpRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllReviews(): Observable<Response<List<Review>>> {
        return lensApiInterface.getAllReviews()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getReviewById(reviewId: Int): Observable<Response<Review>> {
        return lensApiInterface.getReviewById(reviewId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteReviewById(reviewId: Int): Observable<Response<ResponseBody>> {
        return lensApiInterface.deleteReviewById(reviewId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun writeReview(title: String, content: String, lensId: Int): Observable<Response<ResponseBody>> {

        val writeReviewRequest = WriteReviewRequest(title, content, lensId)

        return lensApiInterface.writeReview(writeReviewRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun getReviewCommentsByCommentId(reviewId: Int, commentId: Int): Observable<Response<List<Comment>>> {
        return lensApiInterface.getReviewCommentsByCommentId(reviewId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun getCommentsByReviewId(reviewId: Int): Observable<Response<List<Comment>>> {
        return lensApiInterface.getCommentsByReviewId(reviewId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun writeReviewComment(reviewId: Int, contents: String, bundleId: Int?=null): Observable<Response<ResponseBody>> {
        val writeCommentRequest = WriteCommentRequest(contents, bundleId)

        return lensApiInterface.writeReviewComment(reviewId, writeCommentRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun modifyReviewComment(reviewId: Int, commentId: Int, contents: String): Observable<Response<ResponseBody>> {
        val writeCommentRequest = WriteCommentRequest(contents, null)

        return lensApiInterface.modifyReviewComment(reviewId, commentId, writeCommentRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteReviewCommentById(reviewId: Int, commentId: Int): Observable<Response<ResponseBody>> {

        return lensApiInterface.deleteReviewCommentById(reviewId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun getMyArticle(): Observable<Response<List<Article>>> {
        return lensApiInterface.getMyArticle()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyReview(): Observable<Response<List<Review>>> {
        return lensApiInterface.getMyReview()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyComments(): Observable<Response<List<Comment>>>{
        return lensApiInterface.getMyComments()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun modifyNickname(nickname: String): Observable<Response<ResponseBody>> {
        val modifyNicknameRequest = ModifyNicknameRequest(nickname)

        return lensApiInterface.modifyNickname(modifyNicknameRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun postArticleCommentLike(articleId: Int, commentId: Int): Observable<Response<Comment>> {
        return lensApiInterface.postArticleCommentLike(articleId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteArticleCommentLike(articleId: Int, commentId: Int): Observable<Response<Comment>> {
        return lensApiInterface.deleteArticleCommentLike(articleId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun postArticleLike(articleId: Int): Observable<Response<Article>> {
        return lensApiInterface.postArticleLike(articleId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteArticleLike(articleId: Int): Observable<Response<Article>> {
        return lensApiInterface.deleteArticleLike(articleId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun postReviewCommentLike(reviewId: Int, commentId: Int): Observable<Response<Comment>> {
        return lensApiInterface.postReviewCommentLike(reviewId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteReviewCommentLike(reviewId: Int, commentId: Int): Observable<Response<Comment>> {
        return lensApiInterface.deleteReviewCommentLike(reviewId, commentId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun postReviewLike(reviewId: Int): Observable<Response<Review>> {
        return lensApiInterface.postReviewLike(reviewId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun deleteReviewLike(reviewId: Int): Observable<Response<Review>> {
        return lensApiInterface.deleteReviewLike(reviewId)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}