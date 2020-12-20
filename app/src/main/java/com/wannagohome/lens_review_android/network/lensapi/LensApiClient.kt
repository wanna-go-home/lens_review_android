package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.*
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.network.model.review.WriteReviewRequest
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
import com.wannagohome.lens_review_android.network.model.user.MyInfo
import com.wannagohome.lens_review_android.network.model.user.SignUpRequest
import com.wannagohome.lens_review_android.support.AccessKeyHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber


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

    fun getArticleList(): Observable<Response<List<ArticlePreview>>> {
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


    fun writeArticle(title: String, content: String): Observable<Response<ResponseBody>> {

        val writeArticleRequest = WriteArticleRequest(title, content)

        return lensApiInterface.writeArticle(writeArticleRequest)
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

    fun getCommentsByCommentId(articleId: Int, commentId: Int): Observable<Response<List<Comment>>> {
        return lensApiInterface.getCommentsByCommentId(articleId, commentId)
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

    fun getAllReviews(): Observable<Response<List<ReviewPreview>>> {
        return lensApiInterface.getAllReviews()
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

}