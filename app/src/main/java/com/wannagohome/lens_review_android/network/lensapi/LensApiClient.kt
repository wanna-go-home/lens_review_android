package com.wannagohome.lens_review_android.network.lensapi

import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.DetailedLens
import com.wannagohome.lens_review_android.network.model.Lens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response


class LensApiClient(private val lensApiInterface: LensApiInterface) {

    fun getLensList(): Observable<Response<List<Lens>>> {
        return lensApiInterface.getLensList()
            .subscribeOn(Schedulers.io())
            .map{t -> if(t.isSuccessful) t else throw HttpException(t)}
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLensById(lensId: Int): Observable<Response<DetailedLens>> {
        return lensApiInterface.getLensById(lensId)
            .subscribeOn(Schedulers.io())
            .map{t -> if(t.isSuccessful) t else throw HttpException(t)}
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBoardList(): Observable<Response<List<Article>>> {
        return lensApiInterface.getArticleList()
            .subscribeOn(Schedulers.io())
            .map{t -> if(t.isSuccessful) t else throw HttpException(t)}
            .observeOn(AndroidSchedulers.mainThread())
    }
}