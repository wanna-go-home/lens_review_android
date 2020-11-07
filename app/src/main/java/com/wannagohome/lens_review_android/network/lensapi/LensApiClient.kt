package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.DetailedArticle
import com.wannagohome.lens_review_android.network.model.DetailedLens
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.network.model.user.LoginRequest
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

    fun getArticleList(): Observable<Response<List<Article>>> {
        return lensApiInterface.getArticleList()
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getArticleById(articleId: Int): Observable<Response<DetailedArticle>> {
        return lensApiInterface.getArticleById(articleId)
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

    fun signUp(email: String, pw: String, phoneNumber: String, nickname: String): Observable<Response<ResponseBody>> {
        val signUpRequest = SignUpRequest("aaa", email, pw, phoneNumber, nickname)
        Timber.d("sfsfslslsl")
        return lensApiInterface.signUp(signUpRequest)
            .subscribeOn(Schedulers.io())
            .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
            .observeOn(AndroidSchedulers.mainThread())
    }

}