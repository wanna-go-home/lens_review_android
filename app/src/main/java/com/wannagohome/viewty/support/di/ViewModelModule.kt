package com.wannagohome.viewty.support.di

import com.wannagohome.viewty.ui.MainViewModel
import com.wannagohome.viewty.ui.bulletin.article.detail.ArticleViewModel
import com.wannagohome.viewty.ui.bulletin.article.list.BoardViewModel
import com.wannagohome.viewty.ui.bulletin.article.write.WriteArticleViewModel
import com.wannagohome.viewty.ui.bulletin.review.review_detail.comment.ReviewCommentViewModel
import com.wannagohome.viewty.ui.bulletin.article.detail.comment.ArticleCommentViewModel
import com.wannagohome.viewty.ui.lens_detail.LensDetailViewModel
import com.wannagohome.viewty.ui.login.LoginViewModel
import com.wannagohome.viewty.ui.mypage.MypageViewModel
import com.wannagohome.viewty.ui.mypage.myarticle.MyArticleViewModel
import com.wannagohome.viewty.ui.mypage.myarticlecomment.MyCommentViewModel
import com.wannagohome.viewty.ui.mypage.myreview.MyReviewViewModel
import com.wannagohome.viewty.ui.bulletin.review.review_detail.ReviewDetailViewModel
import com.wannagohome.viewty.ui.bulletin.review.review_list.ReviewPreviewViewModel
import com.wannagohome.viewty.ui.bulletin.review.write.SelectLensViewModel
import com.wannagohome.viewty.ui.bulletin.review.write.WriteReviewViewModel
import com.wannagohome.viewty.ui.search_lens.LensViewModel
import com.wannagohome.viewty.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LensViewModel() }
    viewModel { BoardViewModel() }
    viewModel { LensDetailViewModel() }
    viewModel { (reviewId: Int) ->
        ReviewDetailViewModel(reviewId)
    }
    viewModel { (reviewId: Int, parentCommentId: Int?) ->
        ReviewCommentViewModel(reviewId, parentCommentId)
    }
    viewModel { (articleId: Int) ->
        ArticleViewModel(articleId)
    }
    viewModel { (articleId: Int) ->
        WriteArticleViewModel(articleId)
    }
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
    viewModel { MyCommentViewModel() }
    viewModel { MyReviewViewModel() }
    viewModel { MainViewModel() }
}