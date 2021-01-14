package com.wannagohome.lens_review_android.support.di

import com.wannagohome.lens_review_android.ui.article.detail.ArticleViewModel
import com.wannagohome.lens_review_android.ui.article.list.BoardViewModel
import com.wannagohome.lens_review_android.ui.article.write.WriteArticleViewModel
import com.wannagohome.lens_review_android.ui.review.review_detail.comment.ReviewCommentViewModel
import com.wannagohome.lens_review_android.ui.article.detail.comment.ArticleCommentViewModel
import com.wannagohome.lens_review_android.ui.lens_detail.LensDetailViewModel
import com.wannagohome.lens_review_android.ui.login.LoginViewModel
import com.wannagohome.lens_review_android.ui.mypage.MypageViewModel
import com.wannagohome.lens_review_android.ui.mypage.myarticle.MyArticleViewModel
import com.wannagohome.lens_review_android.ui.review.review_detail.ReviewDetailViewModel
import com.wannagohome.lens_review_android.ui.review.review_list.ReviewPreviewViewModel
import com.wannagohome.lens_review_android.ui.review.write.SelectLensViewModel
import com.wannagohome.lens_review_android.ui.review.write.WriteReviewViewModel
import com.wannagohome.lens_review_android.ui.search_lens.LensViewModel
import com.wannagohome.lens_review_android.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LensViewModel() }
    viewModel { BoardViewModel() }
    viewModel { LensDetailViewModel() }
    viewModel { (reviewId: Int)->
        ReviewDetailViewModel(reviewId) }
    viewModel { (reviewId: Int, parentCommentId: Int?) ->
        ReviewCommentViewModel(reviewId, parentCommentId)
    }
    viewModel { (articleId: Int)->
        ArticleViewModel(articleId) }
    viewModel { (articleId: Int)->
        WriteArticleViewModel(articleId) }
    viewModel { (articleId: Int, parentCommentId: Int?) ->
        ArticleCommentViewModel(articleId, parentCommentId)
    }
    viewModel { ReviewPreviewViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { WriteReviewViewModel() }
    viewModel { MypageViewModel() }
    viewModel { MyArticleViewModel() }
    viewModel { SelectLensViewModel() }
}